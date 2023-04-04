package com.search.instagramsearching.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.search.instagramsearching.CacheKey;
import com.search.instagramsearching.jwt.util.JwtUtil;
import com.search.instagramsearching.jwt.util.TokenProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
public class RedisConfig {

    @Value("${spring.redis.port}")
    public int port;

    @Value("${spring.redis.host}")
    public String host;

    @Value("${spring.redis.password}")
    public String password;

    @Autowired
    public ObjectMapper objectMapper;

//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//        redisTemplate.setConnectionFactory(connectionFactory);
//        return redisTemplate;
//    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setPassword(password);
        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);
        return connectionFactory;
    }





        @Bean(name = "cacheManager")
        public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {

            RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
                    .disableCachingNullValues()
                    .entryTtl(Duration.ofSeconds(TokenProperties.REFRESH_TOKEN_VALID_TIME))
                    .computePrefixWith(CacheKeyPrefix.simple())
                    .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()));

            Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
            // Refresh
            cacheConfigurations.put(CacheKey.REFRESH, RedisCacheConfiguration.defaultCacheConfig()
                    .entryTtl(Duration.ofSeconds(CacheKey.REFRESH_TOKEN_VALID_TIME)));

            return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(connectionFactory).cacheDefaults(configuration)
                    .withInitialCacheConfigurations(cacheConfigurations).build();
        }
    
}