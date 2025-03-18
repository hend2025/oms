package com.aeye.common.cache.redisConfig;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * Created by klaus on 2019/4/27.
 */
public interface RCacheKeyGenerator {
    /**
    * 获取AOP参数,生成指定缓存Key
    *
    * @param pjp PJP
    * @return 缓存KEY
    */
    String getLockKey(ProceedingJoinPoint pjp);
}
