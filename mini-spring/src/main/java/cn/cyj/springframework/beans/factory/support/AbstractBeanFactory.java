package cn.cyj.springframework.beans.factory.support;

import cn.cyj.springframework.beans.BeansException;
import cn.cyj.springframework.beans.factory.BeanFactory;
import cn.cyj.springframework.beans.factory.config.BeanDefinition;

// 重要方法：
// 1.实现BeanFactory接口中定义的getBean()
// 2.实现ConfigurableBeanFactory接口中定义的addBeanPostProcessor()。即AbstractBeanFactory类持有一个List<BeanPostProcessor>
// 3.定义了抽象方法createBean()，交由AbstractAutowireCapableBeanFactory实现
// 4.定义了抽象方法getBeanDefinition()，交由DefaultListableBeanFactory实现
// 5.AbstractBeanFactory继承自DefaultSingletonBeanRegistry，拥有了操作单例的能力
// 6.spring源码中AbstractBeanFactory会继承FactoryBeanRegistrySupport，该类提供了FactoryBean相关的注册和获取方法。通过FactoryBean可以将普通Bean转换为工厂Bean
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    @Override
    public Object getBean(String name) throws BeansException {
        return doGetBean(name, null);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return doGetBean(name, args);
    }

    protected <T> T doGetBean(final String name, final Object[] args) {
        // spring源码中还有很多的逻辑
        Object bean = getSingleton(name);
        if (bean != null) {
            return (T) bean;
        }

        // 模版方法getBeanDefinition()与createBean()，具体交由子类实现，实现类职能的分离
        BeanDefinition beanDefinition = getBeanDefinition(name);
        return (T) createBean(name, beanDefinition, args);
    }

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException;
}