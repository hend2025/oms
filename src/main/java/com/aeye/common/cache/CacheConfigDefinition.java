package com.aeye.common.cache;

import com.aeye.common.config.ApplicationConstant;
import com.aeye.common.utils.SpringContextUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.cache.RedisCacheManager;

@Configuration
@DependsOn({"springContextUtils"})
public class CacheConfigDefinition extends CachingConfigurerSupport {

    @Value("${spring.cache.type}")
    private String cacheType;


    /**
     * cacheManager名字
     */
    public interface CacheManagerNames {
        /**
         * redis
         */
        String REDIS_CACHE_MANAGER = "redisCacheManager";
        /**
         * ehCache
         */
        String EHCACHE_CACHE_MAANGER = "ehCacheCacheManager";
    }
    /**
     * 根据application 配置的缓存类型，返回自定义默认的缓存实现对象，如果不覆盖这个方法根据顺序返回
     * @return
     */
    @Override
    public CacheManager cacheManager() {
        if(ApplicationConstant.CACHE_TYPE_REDIS.equals(cacheType)){
            return SpringContextUtils.getBean(RedisCacheManager.class);
        }else{
            try{
                return SpringContextUtils.getBean(EhCacheCacheManager.class);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return null;
    }
}