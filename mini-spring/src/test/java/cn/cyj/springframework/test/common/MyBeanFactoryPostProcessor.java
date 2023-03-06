package cn.cyj.springframework.test.common;

import cn.cyj.springframework.beans.BeansException;
import cn.cyj.springframework.beans.PropertyValue;
import cn.cyj.springframework.beans.PropertyValues;
import cn.cyj.springframework.beans.factory.ConfigurableListableBeanFactory;
import cn.cyj.springframework.beans.factory.config.BeanDefinition;
import cn.cyj.springframework.beans.factory.config.BeanFactoryPostProcessor;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("userService");
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("uId", "2"));
    }
}
