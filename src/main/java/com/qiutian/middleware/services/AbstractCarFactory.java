package com.qiutian.middleware.services;

public abstract class AbstractCarFactory {
    public abstract Engine createEngineFactory(String type);

    public abstract Wheel createWheelFactory();
}
