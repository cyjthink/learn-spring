package cn.cyj.springframework.context;

import java.util.EventListener;

// 事件监听器，通过<E extends ?>实现监听不同事件
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {

    // 监听器触发时的方法
    void onApplicationEvent(E event);
}
