package cn.cyj.springframework.beans.factory.config;

import cn.cyj.springframework.beans.BeansException;
import com.sun.istack.internal.Nullable;

// 每个对象创建时都会调用一次
// 疑问：
// 1.postProcessBeforeInitialization、postProcessAfterInitialization会在什么时候返回null呢？返回null时就会跳出BeanPostProcessor列表的循环了
// 2.BeanPostProcessor的应用场景是怎么样的？
public interface BeanPostProcessor {

    // 在Bean填充属性之后、初始化之前（InitializingBean#afterPropertiesSet()、init-method）调用
    @Nullable
    default Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    // 在初始化之后调用
    @Nullable
    default Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
