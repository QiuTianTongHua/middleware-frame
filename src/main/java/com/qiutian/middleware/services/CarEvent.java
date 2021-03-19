package com.qiutian.middleware.services;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.EventObject;

/**
 * 说明
 *
 * @author qiutian
 * @since 2021/2/19
 */
@Getter
@Setter
public class CarEvent extends EventObject {

    private CarSource carSource;

    public CarEvent(CarSource carSource) {
        super(carSource);
        this.carSource = carSource;
    }
}
