package com.qiutian.middleware.services;

public class BenzCarWheel implements Wheel {
    @Override
    public void createWheel() {
        System.out.println("制造奔驰车轮");
    }
}
