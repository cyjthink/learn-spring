package cn.cyj.springframework.context.event;

import cn.cyj.springframework.beans.factory.BeanFactory;
import cn.cyj.springframework.context.ApplicationEvent;
import cn.cyj.springframework.context.ApplicationListener;

public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {

    public SimpleApplicationEventMulticaster() {
    }

    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }

    // 找到对应的ApplicationListener并调用listener.onApplicationEvent(event)
    @Override
    public void multicastEvent(ApplicationEvent event) {
        for (ApplicationListener listener : getApplicationListeners(event)) {
            listener.onApplicationEvent(event);
        }
    }
}
