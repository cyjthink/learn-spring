package cn.cyj.springframework.beans.factory.support;

import cn.cyj.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// spring中的DefaultSingletonBeanRegistry继承自SimpleAliasRegistry
// 该类实现了注册、获取等单例的能力
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    // 这里使用了线程安全的HashMap
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        // 判断是否存在。如果不存在则将其加入map
        // 如果该bean是原型模式，则不应该进入到这里的逻辑中
        // todo 这里为什么要加synchronized关键字？
        synchronized (this.singletonObjects) {
            Object oldObject = this.singletonObjects.get(beanName);
            if (oldObject != null) {
                throw new IllegalStateException("Could not register object [" + singletonObject +
                        "] under bean name '" + beanName + "': there is already object [" + oldObject + "] bound");
            }
            addSingleton(beanName, singletonObject);
        }
    }

    protected void addSingleton(String beanName, Object singletonObject) {
        // todo singletonObjects本身已经是线程安全的了，为什么还需要在外面加synchronized关键字？
        synchronized (this.singletonObjects) {
            this.singletonObjects.put(beanName, singletonObject);
        }
    }

    @Override
    public Object getSingleton(String beanName) {
        // spring源码中还有通过singletonObjects、earlySingletonObjects获取值的判断，并加了synchronized关键字
        return this.singletonObjects.get(beanName);
    }

    @Override
    public boolean containsSingleton(String beanName) {
        return this.singletonObjects.containsKey(beanName);
    }
}
