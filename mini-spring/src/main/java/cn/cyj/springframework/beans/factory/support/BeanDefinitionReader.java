package cn.cyj.springframework.beans.factory.support;

import cn.cyj.springframework.beans.factory.BeanDefinitionStoreException;
import cn.cyj.springframework.core.io.Resource;
import cn.cyj.springframework.core.io.ResourceLoader;

public interface BeanDefinitionReader {

    BeanDefinitionRegistry getRegistry();

    ResourceLoader getResourceLoader();

    void loadBeanDefinitions(Resource resource) throws BeanDefinitionStoreException;

    void loadBeanDefinitions(Resource... resources) throws BeanDefinitionStoreException;

    void loadBeanDefinitions(String location) throws BeanDefinitionStoreException;

    void loadBeanDefinitions(String... location) throws BeanDefinitionStoreException;
}
