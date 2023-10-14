package org.personales.bookapi.infrastructure.configuration;

import org.redisson.api.RedissonClient;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
public class CacheableConfig {

    @Autowired
    private RedissonClient redissonClient;

    @Bean
    public RedissonSpringCacheManager cacheManager() {
        Map<String, CacheConfig> config = new HashMap<>();
        long ttlInMillis = Duration.ofDays(30).toMillis();
        CacheConfig bookImagesCacheConfig  = new CacheConfig(ttlInMillis, ttlInMillis);
        config.put("bookImages", bookImagesCacheConfig);

        return new RedissonSpringCacheManager(redissonClient, config);
    }

}
