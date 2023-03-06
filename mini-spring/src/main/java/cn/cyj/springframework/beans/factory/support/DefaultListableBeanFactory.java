package cn.cyj.springframework.beans.factory.support;

import cn.cyj.springframework.beans.BeansException;
import cn.cyj.springframework.beans.factory.BeanDefinitionStoreException;
import cn.cyj.springframework.beans.factory.ConfigurableListableBeanFactory;
import cn.cyj.springframework.beans.factory.NoSuchBeanDefinitionException;
import cn.cyj.springframework.beans.factory.config.BeanDefinition;

import java.util.HashMap;
import java.util.Map;

// 重点方法：
// 1. 实现了BeanFactory接口中定义的getBean(Class<T> requiredType)，即平时用的beanFactory.getBean(Person.class)
// 2. 实现了AbstractBeanFactory中定义的抽象方法getBeanDefinition(String beanName)
// 2. 实现了ListableBeanFactory接口中定义的getBeansOfType(Class<T> type)
// 3. 实现了ListableBeanFactory接口中定义的getBeansWithAnnotation(Class<? extends Annotation> annotationType)
// 4. 实现了BeanDefinitionRegistry接口中定义的getBeanDefinitionNames()
// 6. 实现了BeanDefinitionRegistry接口中定义的registerBeanDefinition(String beanName, BeanDefinition beanDefinition)
// 7. 实现了BeanDefinitionRegistry接口中定义的removeBeanDefinition(String beanName)
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {

    private final Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) throws BeanDefinitionStoreException {
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null)
            throw new NoSuchBeanDefinitionException("No bean named '" + beanName + "' found in " + this);
        return beanDefinition;
    }

    @Override
    public void preInstantiateSingletons() throws BeansException {
        // todo
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        Map<String, T> result = new HashMap<>();
        beanDefinitionMap.forEach((beanName, beanDefinition) -> {
            Class beanClass = beanDefinition.getBeanClass();
            if (type.isAssignableFrom(beanClass)) {
                result.put(beanName, (T) getBean(beanName));
            }
        });
        return result;
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return beanDefinitionMap.keySet().toArray(new String[0]);
    }
}
