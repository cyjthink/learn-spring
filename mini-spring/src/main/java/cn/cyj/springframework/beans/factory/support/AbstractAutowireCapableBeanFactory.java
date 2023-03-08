package cn.cyj.springframework.beans.factory.support;

import cn.cyj.springframework.beans.BeansException;
import cn.cyj.springframework.beans.PropertyValue;
import cn.cyj.springframework.beans.PropertyValues;
import cn.cyj.springframework.beans.factory.*;
import cn.cyj.springframework.beans.factory.config.BeanDefinition;
import cn.cyj.springframework.beans.factory.config.BeanPostProcessor;
import cn.cyj.springframework.beans.factory.config.BeanReference;
import cn.hutool.core.bean.BeanUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import cn.hutool.core.util.StrUtil;

// 重点：
// 1.bean的创建策略：InstantiationStrategy instantiationStrategy
// todo 比较jdk动态代理和cglib动态代理的区别
// 2.实现AbstractBeanFactory中定义的抽象方法createBean()
// 3.实现AutowireCapableBeanFactory接口中的applyBeanPropertyValues()，给Bean设置属性
// 4.调用Aware相关的接口：invokeAwareMethods()
// 5.调用init-method：invokeInitMethods()
// 6.实现AutowireCapableBeanFactory接口中的applyBeanPostProcessorsBeforeInstantiation()，在Bean实例化之前调用BeanPostProcessors
// 6.实现AutowireCapableBeanFactory接口中的applyBeanPostProcessorsBeforeInitialization()，在Bean初始化之前调用BeanPostProcessors
// 7.实现AutowireCapableBeanFactory接口中的applyBeanPostProcessorsAfterInitialization()，在Bean初始化之后调用BeanPostProcessors
// 总结：
// 1.对象创建、初始化过程
    // 1.1.创建对象
    // 1.2.early cache
    // 1.3.设置属性
    // 1.4.调用Aware方法，如BeanFactoryAware。但ApplicationContextAware不同，它通过ApplicationContextAwareProcessor实现
    // 1.4.调用BeanPostProcessor#postProcessBeforeInitialization()
    // 1.5.调用初始化方法：InitializingBean#afterPropertiesSet()或init-method
    // 1.6.调用BeanPostProcessor#postProcessAfterInitialization()
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

//    private InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();
    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException {
        Object beanInstance = doCreateBean(beanName, beanDefinition, args);
        return beanInstance;
    }

    protected Object doCreateBean(String beanName, BeanDefinition beanDefinition, Object[] args) {
        // 1.反射创建对象
        Object bean = createBeanInstance(beanDefinition, beanName, args);
        // 2.给对象设置属性
        populateBean(beanName, bean, beanDefinition);
        // 3.初始化对象
        bean = initializeBean(beanName, bean, beanDefinition);
        // 4.注册实现了DisoisableBean或者配置了destory-method的对象
        registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);
        // 5.如果是单例则将对象缓存起来(spring源码中是addSingletonFactory()，表示eagerly cache singletons)
        if (beanDefinition.isSingleton()) {
            addSingleton(beanName, bean);
        }
        return bean;
    }

    protected Object createBeanInstance(BeanDefinition beanDefinition, String beanName, Object[] args) {
        Constructor constructorToUse = null;
        Class<?> beanClass = beanDefinition.getBeanClass();
        Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();
        for (Constructor ctor : declaredConstructors) {
            if (null != args && ctor.getParameterTypes().length == args.length) {
                constructorToUse = ctor;
                break;
            }
        }
        return getInstantiationStrategy().instantiate(beanDefinition, beanName, constructorToUse, args);
    }

    protected void populateBean(String beanName, Object bean, BeanDefinition beanDefinition) {
        applyPropertyValues(beanName, bean, beanDefinition);
    }

    protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        try {
            PropertyValues propertyValues = beanDefinition.getPropertyValues();
            for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {

                String name = propertyValue.getName();
                Object value = propertyValue.getValue();

                // 处理属性中引用类型对象
                if (value instanceof BeanReference) {
                    // A 依赖 B，获取 B 的实例化
                    BeanReference beanReference = (BeanReference) value;
                    value = getBean(beanReference.getBeanName());
                }
                // 属性填充
                BeanUtil.setFieldValue(bean, name, value);
            }
        } catch (Exception e) {
            throw new BeansException("Error setting property values：" + beanName);
        }
    }

    protected Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {
        // 1.调用aware方法
        invokeAwareMethods(beanName, bean);
        // 2.调用postProcessBeforeInitialization
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);
        // 3.调用初始化方法(init-method)
        try {
            invokeInitMethods(beanName, wrappedBean, beanDefinition);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 4.调用postProcessAfterInitialization
        wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
        return wrappedBean;
    }

    private void invokeAwareMethods(String beanName, Object bean) {
        if (bean instanceof Aware) {
            if (bean instanceof BeanNameAware) {
                ((BeanNameAware) bean).setBeanName(beanName);
            }
            if (bean instanceof BeanClassLoaderAware) {
                ClassLoader bcl = getBeanClassLoader();
                if (bcl != null) {
                    ((BeanClassLoaderAware) bean).setBeanClassLoader(bcl);
                }
            }
            if (bean instanceof BeanFactoryAware) {
                ((BeanFactoryAware) bean).setBeanFactory(AbstractAutowireCapableBeanFactory.this);
            }
        }
    }

    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName)
            throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessBeforeInitialization(existingBean, beanName);
            if (current == null) {
                return result;
            }
            result = current;
        }
        return existingBean;
    }

    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) {
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessAfterInitialization(existingBean, beanName);
            if (current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }

    // 调用两种初始化方法，1.实现InitializingBean接口 2.配置init-method
    protected void invokeInitMethods(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception {
        // 1.调用InitializingBean#afterPropertiesSet()
        if (bean instanceof InitializingBean) {
            ((InitializingBean) bean).afterPropertiesSet();
        }

        // 2.调用init-method(判断是为了避免二次执行销毁)
        String initMethodName = beanDefinition.getInitMethodName();
        if (StrUtil.isNotEmpty(initMethodName)) {
            Method initMethod = beanDefinition.getBeanClass().getMethod(initMethodName);
            if (null == initMethod) {
                throw new BeansException("Could not find an init method named '" + initMethodName + "' on bean with name '" + beanName + "'");
            }
            initMethod.invoke(bean);
        }
    }

    // spring源码中该方法在AbstractBeanFactory
    protected void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
        // 非Singleton类型的bean不执行销毁方法
        if (!beanDefinition.isSingleton()) return;

        if (bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())) {
            registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
        }
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }
}
