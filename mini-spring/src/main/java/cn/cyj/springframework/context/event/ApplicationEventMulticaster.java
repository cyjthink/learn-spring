package cn.cyj.springframework.context.event;

import cn.cyj.springframework.context.ApplicationEvent;
import cn.cyj.springframework.context.ApplicationListener;

// Multicaster：组播
// ApplicationEventMulticaster为中间管理者，提供注册、移除监听器，组播（通知监听器）的功能
public interface ApplicationEventMulticaster {

    void addApplicationListener(ApplicationListener<?> listener);

    void removeApplicationListener(ApplicationListener<?> listener);

    // 通知传入的ApplicationEvent对应的监听器
    void multicastEvent(ApplicationEvent event);
}
