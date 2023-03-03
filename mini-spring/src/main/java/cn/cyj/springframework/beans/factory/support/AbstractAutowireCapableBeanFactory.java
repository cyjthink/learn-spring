package cn.cyj.springframework.beans.factory.support;

import cn.cyj.springframework.beans.BeansException;
import cn.cyj.springframework.beans.PropertyValue;
import cn.cyj.springframework.beans.PropertyValues;
import cn.cyj.springframework.beans.factory.config.BeanDefinition;
import cn.cyj.springframework.beans.factory.config.BeanReference;
import cn.hutool.core.bean.BeanUtil;

import java.lang.reflect.Constructor;

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
        applyPropertyValues(beanName, bean, beanDefinition);
        // 3.将对象缓存起来，如果是原型类型则不用
        addSingleton(beanName, bean);
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

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }
}
