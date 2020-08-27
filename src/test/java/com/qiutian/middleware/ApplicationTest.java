package com.qiutian.middleware;

import Ths.JDIBridge;
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

    @Test
    public void rLockTest() {
        System.out.println(System.getProperty("java.library.path"));

        System.load("D:\\Ths\\THSDataInterface_Windows\\bin\\x64\\iFinDJava_x64.dll");

        int ret = JDIBridge.THS_iFinDLogin("mdzg001", "666888");
        if (ret == 0) {
            while (true) {
                /*ret = JDIBridge.THS_iFinDLogin("ifind_e001", "test");*/
                String result = JDIBridge.THS_RealtimeQuotes("000003.OF","real_time_valuation");
                System.out.println("=========" + result);


                /*String strResulthis = JDIBridge.THS_HistoryQuotes("002632.SZ", "close", "period:D,pricetype:1,rptcategory:0,fqdate:1900-01-01,hb:YSHB,fill:Omit", "2017-07-09", "2017-07-09");
                System.out.println("THS_iFinDhis ==> " + strResulthis);

                String strResulsnap = JDIBridge.THS_Snapshot("300033.SZ,600000.SH", "preClose;open", "fill:Previous", "2011-06-10 09:30:00", "2011-06-10 10:10:00");
                System.out.println("THS_iFinDhis ==> " + strResulthis);

                strResulthis = JDIBridge.THS_HistoryQuotes("0001.HK,0002.HK,0003.HK,0004.HK,0005.HK,0006.HK", "close", "period:D,pricetype:1,rptcategory:0,fqdate:1900-01-01,hb:YSHB,fill:Previous", "2017-07-09", "2017-07-09");
                System.out.println("THS_iFinDhis ==> " + strResulthis);

                String strResult = JDIBridge.THS_BasicData("300033.SZ", "ths_spj_stock", "2016-08-31,10,100");
                System.out.println("THS_BasicData ==> " + strResult);

                strResult = JDIBridge.THS_BasicData("600000.SH,600004.SH,600006.SH,600007.SH,600008.SH", "ths_gpjc_stock;ths_gpdm_stock;ths_thsdm_stock", ";;");
                System.out.println("THS_BasicData ==> " + strResult);
                strResult = JDIBridge.THS_HistoryQuotes("300033.SZ,600000.SH", "open;low;high;close", "period:W,pricetype:1,rptcategory:1,fqdate:19000101,hb:MHB", "2016-10-10", "2016-11-19");
                System.out.println("THS_HistoryQuotes ==> " + strResult);
                strResult = JDIBridge.THS_DataPool("block", "2016-10-18;001005010", "date:Y,security_name:Y,thscode:Y");
                System.out.println("THS_DataPool ==> " + strResult);
                System.out.println("THS_HighFrequencceSequence ==> " + strResult);

                strResult = JDIBridge.THS_RealtimeQuotes("600266.SH", "close;open;high;low;new;avg;change;price;turnover;volume;mrj1;mrl1;mcj1;mcl1;mrj2;mrl2;mcj2;mcl2;mrj3;mrl3;mcj3;mcl3;mrj4;mrl4;mcj4;mcl4;mrj5;mrl5;mcj5;mcl5;cc;zjlx;zjcd;zf;ccl");
                System.out.println("THS_RealtimeQuotes ==> " + strResult);
                strResult = JDIBridge.THS_EDBQuery("M001889667", "2016-05-19", "2017-05-19");
                System.out.println("THS_EDBQuery ==> " + strResult);


                strResult = JDIBridge.THS_DateSerial("300033.SZ", "ths_qspj_stock;ths_kpj_stock;ths_zgj_stock;ths_zdj_stock;ths_spj_stock", "100,2017-12-13;100,2017-12-13;100,2017-12-13;100,2017-12-13;100,2017-12-13", "Days:Tradedays,Fill:Previous,Interval:D", "2017-11-13", "2017-12-13");
                System.out.println("THS_DateSerial ==> " + strResult);

                strResult = JDIBridge.THS_GetErrorInfo(0);
                System.out.println("THS_GetErrorInfo ==> " + strResult);

                strResult = JDIBridge.THS_DateQuery("SSE", "dateType:trade,period:D,dateFormat:0", "2016-07-21", "2016-08-21");
                System.out.println("THS_DateQuery ==> " + strResult);

                System.out.println("THS_DataOffset ==> " + strResult);

                strResult = JDIBridge.THS_DateCount("SSE", "dateType:trade,period:D,dateFormat:0", "2016-07-21", "2016-08-21");
                System.out.println("THS_DateCount ==> " + strResult);

                strResult = JDIBridge.THS_DataStatistics();
                System.out.println("Datastatistics ==> " + strResult);

*/
                JDIBridge.THS_iFinDLogout();
                System.out.println("THS_iFinDLogout ==> ");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }
}
