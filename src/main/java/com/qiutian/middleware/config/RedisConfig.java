package com.qiutian.middleware.config;

import org.redisson.Config;
import org.redisson.Redisson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author qiutian
 * @date 2020/7/23
 */
@Configuration
public class RedisConfig {

    @Value("${redis.minIdle}")
    private String minIdle;

    @Value("${redis.maxIdle}")
    private String maxIdle;

    @Value("${redis.maxTotal}")
    private String maxTotal;

    @Value("${redis.testOnBorrow}")
    private String testOnBorrow;

    @Value("${redis.clusterName}")
    private String clusterName;

    @Value("${redis1.host}")
    private String host1;

    @Value("${redis1.port}")
    private String port1;

    @Value("${redis2.host}")
    private String host2;

    @Value("${redis2.port}")
    private String port2;

    @Value("${redis3.host}")
    private String host3;

    @Value("${redis3.port}")
    private String port3;

    private static Config config = new Config();

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMinIdle(Integer.valueOf(minIdle));
        jedisPoolConfig.setMaxIdle(Integer.valueOf(maxIdle));
        jedisPoolConfig.setMaxTotal(Integer.valueOf(maxTotal));
        jedisPoolConfig.setTestOnBorrow(Boolean.valueOf(testOnBorrow));
        return jedisPoolConfig;
    }

    @Bean
    public RedisSentinelConfiguration redisSentinelConfiguration() {
        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
        RedisNode redisNode = new RedisNode.RedisNodeBuilder().withName(clusterName).build();
        RedisNode redisNode1 = new RedisNode(host1,Integer.valueOf(port1));
        RedisNode redisNode2 = new RedisNode(host2,Integer.valueOf(port2));
        RedisNode redisNode3 = new RedisNode(host3,Integer.valueOf(port3));
        Set<RedisNode> redisNodes = new HashSet<>();
        redisNodes.add(redisNode1);
        redisNodes.add(redisNode2);
        redisNodes.add(redisNode3);
        redisSentinelConfiguration.setMaster(redisNode);
        redisSentinelConfiguration.setSentinels(redisNodes);
        return redisSentinelConfiguration;
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(){
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisSentinelConfiguration());
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig());
        return jedisConnectionFactory;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(){
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(jedisConnectionFactory());
        return stringRedisTemplate;
    }

    @Bean
    public Redisson redisson() {
        config.useSentinelConnection().setMasterName(redisSentinelConfiguration().getMaster().getName());
        config.useSentinelConnection().setRetryInterval(1000);
        config.useSentinelConnection().setRetryAttempts(1);
        Set<RedisNode> nodes = redisSentinelConfiguration().getSentinels();
        String[] adds = new String[nodes.size()];
        int position = 0;
        for(RedisNode node : nodes){
            adds[position] = node.getHost() + ":" + node.getPort();
            position ++;
        }
        config.useSentinelConnection().addSentinelAddress(adds);
        return Redisson.create(config);
    }
}
