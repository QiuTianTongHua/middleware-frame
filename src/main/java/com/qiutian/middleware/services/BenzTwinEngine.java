package com.qiutian.middleware.services;

public class BenzTwinEngine implements Engine {
    @Override
    public void createEngine() {
        System.out.println("制造奔驰车双引擎");
    }
}
