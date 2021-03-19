package com.qiutian.middleware.services;

import org.apache.commons.lang3.StringUtils;

public class BenzCarFactory extends AbstractCarFactory {

    @Override
    public Engine createEngineFactory(String type) {
        if (StringUtils.isBlank(type)) {
            return null;
        }
        if (type.equals("TWIN")) {
            return new BenzTwinEngine();
        }
        if (type.equals("SINGLE")) {
            return new BenzCarSingleEngine();
        }
        return null;
    }

    @Override
    public Wheel createWheelFactory() {
        return new BenzCarWheel();
    }
}
