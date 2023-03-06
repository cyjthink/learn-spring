package cn.cyj.springframework.beans.factory.support;

import cn.cyj.springframework.beans.BeansException;
import cn.cyj.springframework.beans.factory.BeanFactory;
import cn.cyj.springframework.beans.factory.config.BeanDefinition;
import cn.cyj.springframework.beans.factory.config.BeanPostProcessor;
import cn.cyj.springframework.beans.factory.config.ConfigurableBeanFactory;

import java.util.ArrayList;
import java.util.List;

// 重要方法：
// 1.实现BeanFactory接口中定义的getBean()
// 2.实现ConfigurableBeanFactory接口中定义的addBeanPostProcessor()。即AbstractBeanFactory类持有一个List<BeanPostProcessor>
// 3.定义了抽象方法createBean()，交由AbstractAutowireCapableBeanFactory实现
// 4.定义了抽象方法getBeanDefinition()，交由DefaultListableBeanFactory实现
// 5.AbstractBeanFactory继承自DefaultSingletonBeanRegistry，拥有了操作单例的能力
// 6.spring源码中AbstractBeanFactory会继承FactoryBeanRegistrySupport，该类提供了FactoryBean相关的注册和获取方法。通过FactoryBean可以将普通Bean转换为工厂Bean
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory {

    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();

    @Override
    public Object getBean(String name) throws BeansException {
        return doGetBean(name, null, null, false);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return doGetBean(name, null, args, false);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return doGetBean(name, requiredType, null, false);
    }

    protected <T> T doGetBean(String name, Class<T> requiredType, Object[] args, boolean typeCheckOnly) {
        // spring源码中还有很多的逻辑
        Object bean = getSingleton(name);
        if (bean != null) {
            return (T) bean;
        }

        // 模版方法getBeanDefinition()与createBean()，具体交由子类实现，实现类职能的分离
        BeanDefinition beanDefinition = getBeanDefinition(name);
        return (T) createBean(name, beanDefinition, args);
    }

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        synchronized (beanPostProcessors) {
            beanPostProcessors.remove(beanPostProcessor);
            beanPostProcessors.add(beanPostProcessor);
        }
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException;
}
