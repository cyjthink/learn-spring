package cn.cyj.springframework.beans.factory;

import cn.cyj.springframework.beans.BeansException;

// 定义了获取单个Bean的方法，如根据beanName或Class
public interface BeanFactory {

    Object getBean(String name) throws BeansException;

    Object getBean(String name, Object... args) throws BeansException;

    <T> T getBean(String name, Class<T> requiredType) throws BeansException;
}
