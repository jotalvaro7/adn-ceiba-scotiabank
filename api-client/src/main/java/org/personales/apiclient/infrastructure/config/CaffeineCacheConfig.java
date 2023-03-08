package org.personales.apiclient.infrastructure.config;


import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.TimeUnit;

@EnableCaching
@Configuration
public class CaffeineCacheConfig {

    public static final String PRODUCTS_CACHE = "PRODUCTS_CACHE";

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        CaffeineCache productsCache = new CaffeineCache(PRODUCTS_CACHE,
                Caffeine.newBuilder().expireAfterAccess(2, TimeUnit.MINUTES).build());
        simpleCacheManager.setCaches(List.of(productsCache));
        return simpleCacheManager;
    }


}
