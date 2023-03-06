package cn.cyj.springframework.test;

import cn.cyj.springframework.beans.PropertyValue;
import cn.cyj.springframework.beans.PropertyValues;
import cn.cyj.springframework.beans.factory.config.BeanDefinition;
import cn.cyj.springframework.beans.factory.config.BeanReference;
import cn.cyj.springframework.beans.factory.support.DefaultListableBeanFactory;
import cn.cyj.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import cn.cyj.springframework.context.support.ClassPathXmlApplicationContext;
import cn.cyj.springframework.core.io.DefaultResourceLoader;
import cn.cyj.springframework.test.bean.UserDao;
import cn.cyj.springframework.test.bean.UserService;
import org.junit.Before;
import org.junit.Test;

public class ApiTest {

    private DefaultResourceLoader resourceLoader;

    @Before
    public void initResourceLoader() {
        resourceLoader = new DefaultResourceLoader();
    }

    @Test
    public void testProperty() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        beanFactory.registerBeanDefinition("userDao", new BeanDefinition(UserDao.class));

        PropertyValues pvs = new PropertyValues();
        pvs.addPropertyValue(new PropertyValue("uId", "1"));
        pvs.addPropertyValue(new PropertyValue("userDao", new BeanReference("userDao")));

        BeanDefinition beanDefinition = new BeanDefinition(UserService.class, pvs);
        beanFactory.registerBeanDefinition("userService", beanDefinition);

        UserService userService = (UserService) beanFactory.getBean("userService", "cyj");
        userService.queryUserInfo();
    }

    @Test
    public void testClassPathResourceLoad() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("classpath:spring.xml");

        UserService userService = (UserService) beanFactory.getBean("userService", UserService.class);
        userService.queryUserInfo();
    }

    @Test
    public void testBeanFactoryPostProcessorAndBeanPostProcessor() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring.xml");

        UserService userService = context.getBean("userService", UserService.class);
        userService.queryUserInfo();
    }
}
