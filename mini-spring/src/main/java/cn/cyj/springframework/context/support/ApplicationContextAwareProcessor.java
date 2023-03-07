package cn.cyj.springframework.context.support;

import cn.cyj.springframework.beans.BeansException;
import cn.cyj.springframework.beans.factory.config.BeanPostProcessor;
import cn.cyj.springframework.context.ApplicationContext;
import cn.cyj.springframework.context.ApplicationContextAware;

// ApplicationContextAware的作用是给每一个实现该接口的bean传递ApplicationContext
// 所以ApplicationContextAwareProcessor需要实现：
// 1.持有ApplicationContext
// 2.每个bean创建时都要传递ApplicationContext
// 具体实现：
// 1.在AbstractApplicationContext中实例化ApplicationContextAwareProcessor时传入了ApplicationContext
// 2.实现BeanPostProcessor接口，实现在bean初始化前后调用
// 疑问：
// 1.为什么ApplicationContextAware不和BeanFactoryAware一样在初始化Bean时(invokeAwareMethod())实现？
// 因为AbstractAutowiredCapableBeanFactory中无法获得ApplicationContext
public class ApplicationContextAwareProcessor implements BeanPostProcessor {

    private final ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ApplicationContextAware) {
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
