package com.aeye.common.cache.redisConfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
* 锁的注解
* 创建一个 RCacheLock 注解，属性配置如下
* prefix： 缓存中 key 的前缀
* expire： 过期时间，此处默认为 5 秒
* timeUnit： 超时单位，此处默认为秒
* delimiter： key 的分隔符，将不同参数值分割开来
*/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RCacheLock {
        /**
        * redis 锁key的前缀
        * @return redis 锁key的前缀
        */
        String prefix() default "";
        /**
        * 过期秒数,默认为5秒
        * @return 轮询锁的时间
        */
        int expire() default 10;
        /**
        * 超时时间单位
        * @return 秒
        */
        TimeUnit timeUnit() default TimeUnit.SECONDS;

        /**
        * <p>Key的分隔符（默认 :）</p>
        * <p>生成的Key：N:SO1008:500</p>
        *
        * @return String
        */
        String delimiter() default ":";
}
