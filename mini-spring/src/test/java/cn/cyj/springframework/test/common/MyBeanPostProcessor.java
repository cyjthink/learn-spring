package cn.cyj.springframework.test.common;

import cn.cyj.springframework.beans.BeansException;
import cn.cyj.springframework.beans.factory.config.BeanPostProcessor;
import cn.cyj.springframework.test.bean.UserService;

public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if ("userService".equals(beanName)) {
            UserService userService = (UserService) bean;
            userService.setCompany("NewCompany");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if ("userService".equals(beanName)) {
            UserService userService = (UserService) bean;
            userService.setLocation("NewLocation");
        }
        return bean;
    }
}
