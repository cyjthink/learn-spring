package cn.cyj.springframework.beans.factory.config;

import cn.cyj.springframework.beans.factory.BeanFactory;

// 重要方法：
// 1.给Bean设置属性：applyBeanPropertyValues(Object existingBean, String beanName)
// 2.初始化Bean：Object initializeBean(Object existingBean, String beanName)
// 3.在初始化之前调用BeanPostProcessors：Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName)
// 4.在初始化之后调用BeanPostProcessors：Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName)
public interface AutowireCapableBeanFactory extends BeanFactory {
}
