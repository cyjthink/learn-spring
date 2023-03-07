package cn.cyj.springframework.beans.factory.support;

import cn.cyj.springframework.beans.BeansException;
import cn.cyj.springframework.beans.factory.DisposableBean;
import cn.cyj.springframework.beans.factory.config.BeanDefinition;
import cn.hutool.core.util.StrUtil;

import java.lang.reflect.Method;

// 为什么使用DisposableBeanAdapter？
// 因为目前有两种销毁的方式(实现DisposableBean或配置destory-method)，使用Adapter包装后将调用统一
public class DisposableBeanAdapter implements DisposableBean {

    private final Object bean;
    private final String beanName;
    private String destroyMethodName;

    public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethodName = beanDefinition.getDestroyMethodName();
    }

    @Override
    public void destroy() throws Exception {
        // 1.调用DisposableBean#destroy()
        if (bean instanceof DisposableBean) {
            ((DisposableBean) bean).destroy();
        }

        // 2.调用destroy-method(判断是为了避免二次执行销毁)
        if (StrUtil.isNotEmpty(destroyMethodName) && !(bean instanceof DisposableBean && "destroy".equals(this.destroyMethodName))) {
            Method destroyMethod = bean.getClass().getMethod(destroyMethodName);
            if (null == destroyMethod) {
                throw new BeansException("Couldn't find a destroy method named '" + destroyMethodName + "' on bean with name '" + beanName + "'");
            }
            destroyMethod.invoke(bean);
        }
    }
}
