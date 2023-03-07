package cn.cyj.springframework.context;

import cn.cyj.springframework.beans.BeansException;

import java.io.Closeable;

// 重要方法：
// 1.refresh()，该方法在AbstractApplicationContext中实现
public interface ConfigurableApplicationContext extends ApplicationContext, Lifecycle, Closeable {

    void refresh() throws BeansException, IllegalStateException;

    void registerShutdownHook();

    void close();
}
