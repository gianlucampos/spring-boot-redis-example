package br.com.gianlucampos.spring.boot.redis.example.config;

import java.io.Closeable;
import java.time.Duration;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@SuppressWarnings("deprecation")
@Slf4j
@Configuration
public class CacheConfiguration extends CachingConfigurerSupport {

    @Value("${spring.redis.minutesCache}")
    private long MINUTES_CACHE;

    @Bean
    @Override
    public CacheErrorHandler errorHandler() {
        return new AppCacheErrorHandler();
    }

    public static class AppCacheErrorHandler implements CacheErrorHandler {

        @Override
        public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
            log.error("Error during redis GET", exception);
        }

        @Override
        public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
            log.error("Error during redis PUT", exception);
        }

        @Override
        public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
            log.error("Error during redis EVICT", exception);
        }

        @Override
        public void handleCacheClearError(RuntimeException exception, Cache cache) {
            log.error("Error during redis CLEAR", exception);
        }
    }

    @Bean
    public AppRedisTemplate<?, ?> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        var redisTemplate = new AppRedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    @Bean
    public RedisCacheManager redisCacheManager(LettuceConnectionFactory lettuceConnectionFactory) {
        JdkSerializationRedisSerializer redisSerializer = new JdkSerializationRedisSerializer(getClass().getClassLoader());
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
            .disableCachingNullValues()
            .entryTtl(Duration.ofMinutes(MINUTES_CACHE))
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer));
        redisCacheConfiguration.usePrefix();

        RedisCacheManager redisCacheManager = RedisCacheManager.RedisCacheManagerBuilder
            .fromConnectionFactory(lettuceConnectionFactory)
            .cacheDefaults(redisCacheConfiguration)
            .build();
        redisCacheManager.setTransactionAware(true);
        return redisCacheManager;
    }

    public static class AppRedisTemplate<K, V> extends RedisTemplate<K, V> {

        @Override
        public <T> T execute(RedisCallback<T> action) {
            try {
                return super.execute(action);
            } catch (Exception e) {
                log.error("Erro ao executar acao no Redis", e);
                return null;
            }
        }

        @Override
        public <T> T execute(RedisCallback<T> action, boolean exposeConnection) {
            try {
                return super.execute(action, exposeConnection);
            } catch (Exception e) {
                log.error("Erro ao executar acao no Redis", e);
                return null;
            }
        }

        @Override
        public <T> T execute(RedisCallback<T> action, boolean exposeConnection, boolean pipeline) {
            try {
                return super.execute(action, exposeConnection, pipeline);
            } catch (Exception e) {
                log.error("Erro ao executar acao no Redis", e);
                return null;
            }
        }

        @Override
        public <T> T execute(SessionCallback<T> session) {
            try {
                return super.execute(session);
            } catch (Exception e) {
                log.error("Erro ao executar acao no Redis", e);
                return null;
            }
        }

        @Override
        public List<Object> executePipelined(SessionCallback<?> session) {
            try {
                return super.executePipelined(session);
            } catch (Exception e) {
                log.error("Erro ao executar acao no Redis", e);
                return null;
            }
        }

        @Override
        public List<Object> executePipelined(SessionCallback<?> session, RedisSerializer<?> resultSerializer) {
            try {
                return super.executePipelined(session, resultSerializer);
            } catch (Exception e) {
                log.error("Erro ao executar acao no Redis", e);
                return null;
            }
        }

        @Override
        public List<Object> executePipelined(RedisCallback<?> action) {
            try {
                return super.executePipelined(action);
            } catch (Exception e) {
                log.error("Erro ao executar acao no Redis", e);
                return null;
            }
        }

        @Override
        public List<Object> executePipelined(RedisCallback<?> action, RedisSerializer<?> resultSerializer) {
            try {
                return super.executePipelined(action, resultSerializer);
            } catch (Exception e) {
                log.error("Erro ao executar acao no Redis", e);
                return null;
            }
        }

        @Override
        public <T> T execute(RedisScript<T> script, List<K> keys, Object... args) {
            try {
                return super.execute(script, keys, args);
            } catch (Exception e) {
                log.error("Erro ao executar acao no Redis", e);
                return null;
            }
        }

        @Override
        public <T> T execute(RedisScript<T> script, RedisSerializer<?> argsSerializer, RedisSerializer<T> resultSerializer, List<K> keys,
            Object... args) {
            try {
                return super.execute(script, argsSerializer, resultSerializer, keys, args);
            } catch (Exception e) {
                log.error("Erro ao executar acao no Redis", e);
                return null;
            }
        }

        @Override
        public <T extends Closeable> T executeWithStickyConnection(RedisCallback<T> callback) {
            try {
                return super.executeWithStickyConnection(callback);
            } catch (Exception e) {
                log.error("Erro ao executar acao no Redis", e);
                return null;
            }
        }
    }
}
