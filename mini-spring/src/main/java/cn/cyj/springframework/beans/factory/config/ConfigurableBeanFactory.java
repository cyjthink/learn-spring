package cn.cyj.springframework.beans.factory.config;

import cn.cyj.springframework.beans.factory.HierarchicalBeanFactory;

// 重要的方法：
// 1. 添加BeanPostProcessor到列表：void addBeanPostProcessor(BeanPostProcessor beanPostProcessor)，该方法在AbstractBeanFactory中实现
// 2. 注册scope：void registerScope(String scopeName, Scope scope);
// 3. 注册别名：void registerAlias(String beanName, String alias)
// 4. 判断是否为FactoryBean类型：boolean isFactoryBean(String name)
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {

    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    ClassLoader getBeanClassLoader();
}
