package com.qiutian.middleware.dto;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * 说明
 *
 * @author qiutian
 * @since 2020/12/3
 */
public class Singleton implements Serializable {
    private static final long serialVersionUID = 1L;
    private static Singleton instance;
    private Singleton(){

    }

    private static final int size = 1000000;

    /**
     * 双重检测单利模式：线程安全，效率较高
     * @return
     */
    public static Singleton getInstance(){
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }

        return instance;
    }

    /**
     * 在单例模式中实现readResolve和writeReplace两个方法，
     * 在序列化和反序列化是得到的对象是同一个对象，
     * 如果没有实现这两个方法，得到的对象则不是同一个对象
     * */
    private Object readResolve(){
        return getInstance();
    }

    private Object writeReplace(){
        return getInstance();
    }

    static BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()), size, 0.001);

    public static boolean isExist() {
        bloomFilter.put("aa");
        if (bloomFilter.mightContain("aa")) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public static void main(String[] args) {
        // boolean b = Singleton.isExist();
        ByteBuffer buffer = ByteBuffer.allocate(8);
        long b = 8L;
        byte [] bytes = buffer.putLong(0, b).array();
        long a = (bytes[7] & 0xFFL) << 56;
        int aa = -1;
        int aaa = aa >> 56;
        System.out.println("结果：{}" + b);
    }

}
