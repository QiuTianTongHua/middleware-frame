package com.qiutian.middleware;

import Ths.JDIBridge;
import com.alibaba.fastjson.JSON;
import com.qiutian.middleware.services.*;
import com.qiutian.middleware.dto.ThsValuationResDTO;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import lombok.extern.slf4j.Slf4j;
import org.redisson.core.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import static Ths.JDIBridge.THS_RealtimeQuotes;

/**
 * Unit test for simple App.
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ApplicationTest extends TestCase {

    @Autowired
    private RedisServices redisServices;

    @Autowired
    private MyPublisher myPublisher;
    @Autowired
    private MyEvent myEvent;

    @Autowired
    private CarSource carSource;

    AtomicBoolean aBoolean = new AtomicBoolean(false);

    @Test
    public void rLockTest() throws InterruptedException {
        RLock lock = redisServices.lock("testRedisson", 30);
        Thread.sleep(1000 * 60 * 2);
        log.info("获取锁结果：{}", JSON.toJSONString(lock));
    }

    @Test
    public void transactionsTest() {
        String desEn = "";
        try {
            // 从原始密匙数据创建DESKeySpec对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES");
            // 用密匙初始化Cipher对象
            // 现在，获取数据并加密
            byte[] destBytes = cipher.doFinal("130928198905281793".getBytes(StandardCharsets.UTF_8));
            StringBuilder hexRetSB = new StringBuilder();
            for (byte b : destBytes) {
                String hexString = Integer.toHexString(0x00ff & b);
                hexRetSB.append(hexString.length() == 1 ? 0 : "").append(hexString);
            }
            desEn = hexRetSB.toString();
        } catch (Exception e) {
            log.error("DES加密发生错误：", e);

        }

        log.info("获取锁结果：{}", desEn);
    }


    @Test
    public void luaLockTest() throws InterruptedException {

       while (true) {
           switch (1) {
               case 1: {
                   System.out.println("1111111111111111111");
               }
               break;
               case 2: {
                   System.out.println("2222222222222222222222");
               }
               break;
               default: {
                   System.out.println("3333333333333333");
               }
               break;
           }
           System.out.println("ApplicationTest.luaLockTest");
       }
    }

    @Test
    public void releaseLockTest() throws InterruptedException {
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("111", "111");
        String s = stringMap.remove("111");
        System.out.println("");
    }

    @Test
    public void batchTest() {
        long startTime = System.currentTimeMillis();
        redisServices.insertString(null, null, 0L);
        log.info("循环添加耗时：{}", (System.currentTimeMillis() - startTime));
        long startTime1 = System.currentTimeMillis();
        redisServices.pipelinedAdd();
        log.info("管道命令添加耗时：{}", (System.currentTimeMillis() - startTime1));
        log.info("获取锁结果：{}");
    }

    @Test
    public void thsTest() {


    }

    @Test
    public void contextLoads() {
        System.out.println("==========00000");
        myPublisher.publisherEvent(myEvent);
        System.out.println("==========00000");
    }

    @Test
    public void listenerTest() {
        carSource.addListener(event -> {
            if ("CAR".equals(event.getCarSource().getCarName())) {
                System.out.println("This is car");
            } else {
                System.out.println("This is bus");
            }
        });
        CarEvent event = new CarEvent(new CarSource("CAR_"));
        carSource.notifyListener(event);
    }
    @Test
    public void factoryTest() {
        AbstractCarFactory carFactory = FactoryProducer.createFactory("BMW");
        Wheel wheel = carFactory.createWheelFactory();
        wheel.createWheel();
        Engine engine = carFactory.createEngineFactory("SINGLE");
        engine.createEngine();
    }

}
