package com.qiutian.middleware;

import Ths.JDIBridge;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.qiutian.middleware.dto.ThsValuationResDTO;
import com.qiutian.middleware.services.RedisServices;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import lombok.extern.slf4j.Slf4j;
import org.redisson.core.RLock;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.config.TaskExecutorFactoryBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static Ths.JDIBridge.THS_RealtimeQuotes;

/**
 * Unit test for simple App.
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ApplicationTest extends TestCase {


    @Test
    public void rLockTest() {
        System.out.println(System.getProperty("java.library.path"));





        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
