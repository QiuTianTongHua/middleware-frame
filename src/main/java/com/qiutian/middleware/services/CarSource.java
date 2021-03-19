package com.qiutian.middleware.services;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 说明
 *
 * @author qiutian
 * @since 2021/2/19
 */
@Component
@Getter
@Setter
public class CarSource {
    private String carName;

    public CarSource() {

    }

    public CarSource(String carName) {
        this.carName = carName;
    }

    private List<CarListener> listeners = new ArrayList<>();

    public void addListener(CarListener listener) {
        listeners.add(listener);
    }

    public void removeListener(CarListener listener) {
        listeners.remove(listener);
    }

    public void notifyListener(CarEvent event) {
        for (CarListener listener : listeners) {
            listener.remindSafetyBelt(event);
        }
    }
}
