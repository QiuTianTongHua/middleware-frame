package com.qiutian.middleware.services;

import java.util.EventListener;

/**
 * 说明
 *
 * @author qiutian
 * @since 2021/2/19
 */
public interface CarListener extends EventListener {
    void remindSafetyBelt(CarEvent event);
}
