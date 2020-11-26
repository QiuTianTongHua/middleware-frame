package com.qiutian.middleware.services;

import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.core.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author qiutian
 * @date 2020/7/24
 */
@Slf4j
public class RedisServices {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private Redisson redisson;
    public RLock lock(String key, long times) {
        try {
            RLock lock = redisson.getLock(key);
            lock.tryLock(times, TimeUnit.SECONDS);
            return lock;
        } catch (InterruptedException e) {
            log.error("获取分布式锁异常：{}", Throwables.getStackTraceAsString(e));
        }
        return null;
    }
}
