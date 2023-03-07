package cn.cyj.springframework.test.aware;

import cn.cyj.springframework.beans.BeansException;
import cn.cyj.springframework.beans.factory.BeanClassLoaderAware;
import cn.cyj.springframework.beans.factory.BeanFactory;
import cn.cyj.springframework.beans.factory.BeanFactoryAware;
import cn.cyj.springframework.beans.factory.BeanNameAware;
import cn.cyj.springframework.context.ApplicationContext;
import cn.cyj.springframework.context.ApplicationContextAware;

public class MyAware implements ApplicationContextAware, BeanNameAware, BeanClassLoaderAware, BeanFactoryAware {

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println(">>MyAware#setBeanClassLoader call, classLoader=" + classLoader + "<<");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println(">>MyAware#setBeanFactory call, beanFactory=" + beanFactory + "<<");
    }

    @Override
    public void setBeanName(String name) {
        System.out.println(">>MyAware#setBeanName call, name=" + name + "<<");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println(">>MyAware#setApplicationContext call, applicationContext=" + applicationContext + "<<");
    }
}
