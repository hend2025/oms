package com.aeye.common.cache.redisConfig;


import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * Created by klaus on 2019/4/27.
 */

@Aspect
@Component
public class RLockMethodInterceptor {
    private final RCacheKeyGenerator keyGenerator;
    @Autowired
    public RLockMethodInterceptor(RCacheKeyGenerator keyGenerator) {
        this.keyGenerator = keyGenerator;
    }

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Around("execution(public * *(..)) && @annotation(com.aeye.common.cache.redisConfig.RCacheLock)")
    public Object interceptor(ProceedingJoinPoint pjp) {
//        MethodSignature signature = (MethodSignature) pjp.getSignature();
//        Method method = signature.getMethod();
//        RCacheLock lock = method.getAnnotation(RCacheLock.class);
        Signature signature =  pjp.getSignature();//方法签名
        String lockKey = "";
        try {

            Method method = ( (MethodSignature)signature ).getMethod();
            //这个方法才是目标对象上有注解的方法
            Method realMethod = pjp.getTarget().getClass().getDeclaredMethod(signature.getName(), method.getParameterTypes());
            RCacheLock lock = realMethod.getAnnotation(RCacheLock.class);
            if (StringUtils.isEmpty(lock.prefix())) {
                throw new RuntimeException("lock key can't be null...");
            }
            lockKey = keyGenerator.getLockKey(pjp);
            if(StringUtils.isNotEmpty(lockKey)){
                while(true) {
                    //key不存在才能设置成功
                    final Boolean success = redisTemplate.opsForValue().setIfAbsent(lockKey, "");
                    if (success) {
                        redisTemplate.expire(lockKey, lock.expire(), lock.timeUnit());
                        break;
                    } else {
                        //休眠一秒，轮询
                        Thread.sleep(1000);
                    }
                }
            }
            try {
                return pjp.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                throw new RuntimeException("系统异常");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //如果演示的话需要注释该代码;实际应该放开
            redisTemplate.delete(lockKey);
        }
        return null;
    }
}