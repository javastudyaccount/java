package jp.btsol.mahjong.web.fw;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

@Configuration
public class RedisConfig {

    public RedisConfig() {
    }

    @Bean
    public RedisConnectionFactory connectionFactory() {
        LettuceConnectionFactory cf = new LettuceConnectionFactory();
        cf.afterPropertiesSet();
        return cf;
    }

    /**
     * セキュアCookieの設定
     * 
     * @return CookieSerializer
     */
    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();

        // HTTPSで動作している場合はHTTPSでないとCookieを返さないようにする
        serializer.setUseSecureCookie(true);

        // CookieへのアクセスはHTTPプロトコルに限定（SSHとかはNG）
        serializer.setUseHttpOnlyCookie(true);

        return serializer;
    }

    @Bean
    public RedisOperations<Object, Object> redisOperations() {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    @Bean
    public FindByIndexNameSessionRepository sessionRepository(RedisOperations<Object, Object> redisOperations) {
        return new RedisIndexedSessionRepository(redisOperations);
    }
}
