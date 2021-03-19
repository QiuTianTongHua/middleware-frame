package com.qiutian.middleware.services;

import org.apache.commons.lang3.StringUtils;

public class FactoryProducer {
    public static AbstractCarFactory createFactory(String type) {
        if (StringUtils.isBlank(type)) {
            return null;
        }
        if (type.equals("BMW")) {
            return new BMWCarFactory();
        }
        if (type.equals("BENZ")) {
            return new BenzCarFactory();
        }
        return null;
    }
}
