package com.qiutian.middleware.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.core.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author qiutian
 * @date 2020/7/24
 */
@Slf4j
@Component
public class RedisServices {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private Redisson redisson;
    /**
     * Redis分布式锁
     * @param key 键值
     * @param times 获取锁等待时间
     * @return
     */
    public RLock lock(String key, long times) {
        try {
            RLock lock = redisson.getLock(key);
            lock.tryLock(times, TimeUnit.SECONDS);
            lock.unlock();
            return lock;
        } catch (InterruptedException e) {
            log.error("获取分布式锁异常：{}", Throwables.getStackTraceAsString(e));
        }

        return null;
    }

    public void transactions(String key, Object value) {
        redisTemplate.setEnableTransactionSupport(Boolean.TRUE);
        String ss = null;
        SessionCallback<Object> sessionCallback = new SessionCallback<Object>(){
            @Override
            public Object execute(RedisOperations operations) {
                operations.multi();
                operations.opsForValue().set("MSA:AAAA:aaaa", "aaaaaaa");
                String s = (String) operations.opsForValue().get("MSA:SEQUENCE:CUSTOM_KEY:SEQ_PAY_NO");
                log.info("========================", s);
                operations.opsForValue().set("MSA:BBBB:bbbb",s + "bbbb");
                Object val = operations.exec();
                return val;
            }
        };
        redisTemplate.execute(sessionCallback);

      /*  stringRedisTemplate.multi();
        this.insertString("MSA:AAAA:aaaa1", "aaaaaaaaaa", 0);
        this.insertString("MSA:BBBB:bbbb1", "bbbbbbbbbb", 0);
        stringRedisTemplate.exec();*/
    }

    public void batchAdd() {
        for (int i = 0; i < 100000; i ++) {
            this.insertString("MSA:AAAA:aaaa" + i , "redis" + i, 0);
        }
    }

    public void pipelinedAdd() {
        redisTemplate.executePipelined((RedisConnection connection) -> {
            for (int i = 0; i < 100000; i ++) {
                byte[] redisKey = redisTemplate.getStringSerializer().serialize("MSA:BBBB:bbbb" + i);
                byte[] redisValue = redisTemplate.getStringSerializer().serialize("redis" + i);
                connection.set(redisKey, redisValue);
            }
            return null;
        });
    }

    /**
     * 插入缓存对象
     * @param key 缓存key
     * @param value Object对象
     * @param timeout 缓存过期时间(秒)
     * @return true：成功，false：失败
     */
    public boolean insertObject(String key, Object value, long timeout) {
        try {
            return insertString(JSONObject.toJSONString(value), key, timeout);
        } catch (Exception e){
            log.error("RedisManagerImpl insertObject JSON转换异常：{}", e);
            throw e;
        }
    }

    /**
     * 插入缓存对象
     * @param key 缓存key
     * @param value json格式字符串
     * @param timeout 缓存过期时间(秒)
     * @return true：成功，false：失败
     */
    public boolean insertString(String key, String value, long timeout) {
        try {
            boolean result = redisTemplate.execute((RedisConnection c) -> {
                for (int i = 0; i < 10000; i ++) {
                    byte[] redisKey = redisTemplate.getStringSerializer().serialize(key);
                    byte[] redisValue = redisTemplate.getStringSerializer().serialize(value);
                    if (timeout > 0){
                        c.setEx(redisKey, timeout, redisValue);
                    }else {
                        c.set(redisKey, redisValue);
                    }
                }

                return true;
            });
            return result;
        } catch (Exception e){
            log.error("添加redis出现异常：{}", e);
            throw e;
        }
    }


    private static DefaultRedisScript<String> redisScript = new DefaultRedisScript<>();

    private final String EXEC_RESULT = "1";
    /**
     * lua脚本实现分布式锁
     * @param key 键值
     * @param requestId value值
     * @param expireTime 过期时间
     * @return
     */
    public boolean getLock(String key, String requestId, String expireTime) {
        log.info("线程名称：{}", Thread.currentThread().getName());
        String scriptText =
                "if redis.call('setNx',KEYS[1],ARGV[1]) then\n" +
                "    if redis.call('get',KEYS[1])==ARGV[1] then\n" +
                "        return redis.call('expire',KEYS[1],ARGV[2])\n" +
                "    else\n" +
                "        return 0\n" +
                "    end\n" +
                "end";
        redisScript.setScriptText(scriptText);
        redisScript.setResultType(String.class);
        Object result = redisTemplate.execute(redisScript, (RedisSerializer)redisTemplate.getKeySerializer(),
                (RedisSerializer<String>)redisTemplate.getKeySerializer(), Collections.singletonList(key), requestId, expireTime);
        log.info("LUA脚本执行结果：{}", JSON.toJSONString(result.getClass()));
        if(EXEC_RESULT.equals(result.toString())) {
            log.info("线程[{}]获取锁成功", Thread.currentThread().getName());
            return true;
        }
        log.info("线程[{}]获取锁失败", Thread.currentThread().getName());
        return false;
    }

    /**
     * lua删除分布式锁
     * @param key
     * @param requestId
     * @return
     */
    public boolean releaseLock(String key, String requestId) {
        String scriptText =
                "if redis.call('get', KEYS[1]) == ARGV[1] then \n" +
                        "return redis.call('del', KEYS[1]) \n" +
                        "else return 0 \n" +
                        "end";
        redisScript.setScriptText(scriptText);
        redisScript.setResultType(String.class);
        Object result = redisTemplate.execute(redisScript, (RedisSerializer)redisTemplate.getValueSerializer(),
                (RedisSerializer)redisTemplate.getKeySerializer(), Collections.singletonList(key), requestId);
        if(EXEC_RESULT.equals(result.toString())) {
            log.info("线程[{}]释放锁成功", Thread.currentThread().getName());
            return true;
        }
        log.info("线程[{}]释放锁失败", Thread.currentThread().getName());
        return false;
    }

}
