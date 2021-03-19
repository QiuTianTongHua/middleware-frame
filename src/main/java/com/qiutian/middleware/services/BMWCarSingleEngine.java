package com.qiutian.middleware.services;

public class BMWCarSingleEngine implements Engine {
    @Override
    public void createEngine() {
        System.out.println("制造宝马车单引擎");
    }
}
