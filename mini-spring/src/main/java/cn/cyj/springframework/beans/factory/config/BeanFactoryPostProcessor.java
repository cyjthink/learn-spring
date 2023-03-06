package cn.cyj.springframework.beans.factory.config;

import cn.cyj.springframework.beans.BeansException;
import cn.cyj.springframework.beans.factory.ConfigurableListableBeanFactory;

// 只在容器启动时调用一次
public interface BeanFactoryPostProcessor {

    // 在所有BeanDefinition加载完成、Bean实例化之前，提供修改BeanDefinition的机制
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;
}
