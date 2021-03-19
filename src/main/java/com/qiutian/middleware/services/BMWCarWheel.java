package com.qiutian.middleware.services;

public class BMWCarWheel implements Wheel {
    @Override
    public void createWheel() {
        System.out.println("制造宝马车轮");
    }
}
