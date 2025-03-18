package com.aeye.common.cache;

import com.aeye.common.utils.SpringContextUtils;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * Created by klaus on 2018/12/27.
 */
@Component
@CacheConfig(cacheNames = "public")
public class CCacheManager {

    private static CacheManager cacheManager;

    public static CacheManager getCacheManager(){
        if(cacheManager == null){
            CacheConfigDefinition cacheConfigDefinition = SpringContextUtils.getBean(CacheConfigDefinition.class);
            cacheManager = cacheConfigDefinition.cacheManager();
        }
        return cacheManager;
    }

//    @Cacheable(key = "#cacheKey+'_'+#valueKey", cacheManager = "redisCacheManager") //双缓存
    @Cacheable(key = "#cacheKey+'_'+#valueKey")
    public <T> T get(String cacheKey, String valueKey,Class<T> tClass){
        return null;//结合下面put理解，get和put定义key一样，因此只要put触发，get只会从缓存取，不会进入这个方法，如果进入get肯定取不到缓存，固定返回null就行了
    }

//    @CachePut(key = "#cacheKey+'_'+#valueKey",cacheManager = "redisCacheManager") //双缓存)
    @CachePut(key = "#cacheKey+'_'+#valueKey")
    public Object put(String cacheKey, String valueKey, Object valueObj){
        return valueObj;
    }

//    @CacheEvict(key = "#cacheKey+'_'+#valueKey", beforeInvocation=true, cacheManager = "redisCacheManager") //双缓存
    @CacheEvict(key = "#cacheKey+'_'+#valueKey", beforeInvocation=true)
    public void evict(String cacheKey, String valueKey){

    }
}
