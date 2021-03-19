package com.qiutian.middleware.services;

public class BenzCarSingleEngine implements Engine {
    @Override
    public void createEngine() {
        System.out.println("制造奔驰车单引擎");
    }
}
