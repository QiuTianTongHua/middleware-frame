package com.qiutian.middleware;

import com.alibaba.fastjson.JSON;
import com.qiutian.middleware.services.RedisServices;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import lombok.extern.slf4j.Slf4j;
import org.redisson.core.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
/**
 * Unit test for simple App.
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ApplicationTest extends TestCase{
    @Autowired
    private RedisServices redisServices;

    @Test
    public void rLockTest() {
        RLock lock = redisServices.lock("testRedisson", 30);
        log.info("获取锁结果：{}", JSON.toJSONString(lock));
    }
}
