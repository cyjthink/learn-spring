package cn.cyj.springframework.beans.factory.support;

import cn.cyj.springframework.beans.factory.BeanDefinitionStoreException;
import cn.cyj.springframework.beans.factory.NoSuchBeanDefinitionException;
import cn.cyj.springframework.beans.factory.config.BeanDefinition;
import cn.cyj.springframework.core.AliasRegistry;

// 提供了多种操作BeanDefinition方法的接口？如注册、删除、获取等
public interface BeanDefinitionRegistry extends AliasRegistry {

    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition)
            throws BeanDefinitionStoreException;

    BeanDefinition getBeanDefinition(String beanName) throws NoSuchBeanDefinitionException;

    boolean containsBeanDefinition(String beanName);

    String[] getBeanDefinitionNames();
}
