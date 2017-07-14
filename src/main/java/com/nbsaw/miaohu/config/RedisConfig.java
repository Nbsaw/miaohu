package com.nbsaw.miaohu.config;

import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableAutoConfiguration
public class RedisConfig {
    private final static Logger logger = Logger.getLogger(RedisConfig.class);

    @Bean
    public JedisPoolConfig getRedisConfig(){
        return new JedisPoolConfig();
    }

    @Bean
    @ConfigurationProperties(prefix="spring.redis")
    public JedisConnectionFactory getConnectionFactory(){
        JedisConnectionFactory factory = new JedisConnectionFactory();
        JedisPoolConfig config = getRedisConfig();
        factory.setPoolConfig(config);
        logger.info("JedisConnectionFactory bean init success.");
        return factory;
    }

    public StringRedisTemplate getTemplate() {
        StringRedisTemplate template = new StringRedisTemplate(getConnectionFactory());
        template.setEnableTransactionSupport(true);
        return template;
    }
}
