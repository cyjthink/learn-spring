package cn.cyj.springframework.context.support;

import cn.cyj.springframework.beans.BeansException;
import cn.cyj.springframework.beans.factory.ConfigurableListableBeanFactory;
import cn.cyj.springframework.beans.factory.support.DefaultListableBeanFactory;

import java.io.IOException;

// 重要方法：
// 1.实现AbstractApplicationContext中定义的抽象方法refreshBeanFactory()
// 2.实现AbstractApplicationContext中定义的抽象方法closeBeanFactory()
// 3.实现AbstractApplicationContext中定义的抽象方法getBeanFactory()
// 4.定义抽象方法loadBeanDefinitions()，交由AbstractXmlApplicationContext实现
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {

    private DefaultListableBeanFactory beanFactory;

    @Override
    protected void refreshBeanFactory() throws BeansException, IllegalStateException {
        // 1.创建BeanFactory
        DefaultListableBeanFactory beanFactory = createBeanFactory();
        // 2.加载BeanDefinition
        loadBeanDefinitions(beanFactory);
        this.beanFactory = beanFactory;
    }

    protected DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }

    @Override
    protected void closeBeanFactory() {

    }

    @Override
    public ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException {
        return beanFactory;
    }

    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory)
            throws BeansException;
}
