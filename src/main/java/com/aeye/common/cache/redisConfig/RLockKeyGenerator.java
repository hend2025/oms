package com.aeye.common.cache.redisConfig;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Component
public class RLockKeyGenerator implements RCacheKeyGenerator {

    @Override
    public String getLockKey(ProceedingJoinPoint pjp) {
        try {
            MethodSignature signature = (MethodSignature) pjp.getSignature();
            Method method = ((MethodSignature) signature).getMethod();
            //这个方法才是目标对象上有注解的方法
            Method realMethod = pjp.getTarget().getClass().getDeclaredMethod(signature.getName(), method.getParameterTypes());
            RCacheLock lockAnnotation = realMethod.getAnnotation(RCacheLock.class);
            final Object[] args = pjp.getArgs();
            final Parameter[] parameters = method.getParameters();
            StringBuilder builder = new StringBuilder();
            //默认解析方法里面带 RCacheParam 注解的属性,如果没有尝试着解析实体对象中的
            for (int i = 0; i < parameters.length; i++) {
                final RCacheParam annotation = parameters[i].getAnnotation(RCacheParam.class);
                if (annotation == null) {
                    continue;
                }
                builder.append(lockAnnotation.delimiter()).append(args[i]);
            }
            if (StringUtils.isEmpty(builder.toString())) {
                final Annotation[][] parameterAnnotations = method.getParameterAnnotations();
                for (int i = 0; i < parameterAnnotations.length; i++) {
                    final Object object = args[i];
                    final Field[] fields = object.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        final RCacheParam annotation = field.getAnnotation(RCacheParam.class);
                        if (annotation == null) {
                            continue;
                        }
                        field.setAccessible(true);
                        builder.append(lockAnnotation.delimiter()).append(ReflectionUtils.getField(field, object));
                    }
                }
            }
            return lockAnnotation.prefix() + builder.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}