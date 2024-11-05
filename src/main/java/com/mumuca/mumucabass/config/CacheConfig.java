package com.mumuca.mumucabass.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

@Configuration
public class CacheConfig {

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .disableCachingNullValues()
                .entryTtl(Duration.ofDays(30));

//        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
//
//        cacheConfigurations.put("artist", defaultCacheConfig.entryTtl(Duration.ofDays(30)));
//        cacheConfigurations.put("artistTopTracks", defaultCacheConfig.entryTtl(Duration.ofDays(30)));
//
//        cacheConfigurations.put("album", defaultCacheConfig.entryTtl(Duration.ofDays(30)));
//
//        cacheConfigurations.put("track", defaultCacheConfig.entryTtl(Duration.ofDays(30)));

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(defaultCacheConfig)
                //.withInitialCacheConfigurations(cacheConfigurations)
                .build();
    }
}
