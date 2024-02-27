package com.lauro.sifyflixapi.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@EnableCaching
public class CaffeineCacheConfig {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new
                CaffeineCacheManager("getAllCache", "updateCache");
        cacheManager.setCaffeine(this.caffeineCacheBuild());
        return cacheManager;
    }

    @Bean
    public Caffeine caffeineCacheBuild() {
        return Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(100)
                .expireAfterWrite(Duration.ofMinutes(10));
    }
}
