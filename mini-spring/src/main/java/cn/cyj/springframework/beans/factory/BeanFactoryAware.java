package cn.cyj.springframework.beans.factory;

import cn.cyj.springframework.beans.BeansException;

// 获取BeanFactory的感知接口
public interface BeanFactoryAware extends Aware {

    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
