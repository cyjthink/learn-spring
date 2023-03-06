package cn.cyj.springframework.beans.factory;

import cn.cyj.springframework.beans.BeansException;
import cn.cyj.springframework.beans.factory.config.AutowireCapableBeanFactory;
import cn.cyj.springframework.beans.factory.config.BeanDefinition;
import cn.cyj.springframework.beans.factory.config.ConfigurableBeanFactory;

public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {

    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    void preInstantiateSingletons() throws BeansException;
}
