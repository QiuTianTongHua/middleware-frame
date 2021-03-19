package com.qiutian.middleware.services;

import org.apache.commons.lang3.StringUtils;

public class BMWCarFactory extends AbstractCarFactory{

    @Override
    public Engine createEngineFactory(String type) {
        if (StringUtils.isBlank(type)) {
            return null;
        }
        if (type.equals("TWIN")) {
            return new BMWCarTwinEngine();
        }
        if (type.equals("SINGLE")) {
            return new BMWCarSingleEngine();
        }
        return null;
    }

    @Override
    public Wheel createWheelFactory() {
        return new BMWCarWheel();
    }
}
