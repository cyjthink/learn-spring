package cn.cyj.springframework.beans.factory.config;

// 声明了操作单例的能力
public interface SingletonBeanRegistry {

    void registerSingleton(String beanName, Object singletonObject);

    Object getSingleton(String beanName);

    boolean containsSingleton(String beanName);
}
