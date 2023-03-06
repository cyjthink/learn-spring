package cn.cyj.springframework.context.support;

import cn.cyj.springframework.beans.BeansException;
import cn.cyj.springframework.beans.factory.support.DefaultListableBeanFactory;
import cn.cyj.springframework.beans.factory.xml.XmlBeanDefinitionReader;

import java.io.IOException;

// 重要方法：
// 1.实现AbstractRefreshableApplicationContext中定义的抽象方法loadBeanDefinitions()
// 2.定义抽象方法getConfigLocations()，交由ClassPathXmlApplicationContext实现
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableConfigApplicationContext {

    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeansException {
        // 1.创建XmlBeanDefinitionReader
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory, this);
        String[] configLocations = getConfigLocations();
        if (null != configLocations){
            beanDefinitionReader.loadBeanDefinitions(configLocations);
        }
    }

    protected abstract String[] getConfigLocations();
}
