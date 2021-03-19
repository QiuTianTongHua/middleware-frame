package com.qiutian.middleware.services;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 说明
 *
 * @author qiutian
 * @since 2021/2/18
 */
@Component
public class MyListenerA implements ApplicationListener<MyEvent> {
    @Override
    public void onApplicationEvent(MyEvent myEvent) {
        System.out.println("MyListenerA");
        myEvent.echo();
    }
}
