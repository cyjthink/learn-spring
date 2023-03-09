package cn.cyj.springframework.context.support;

import cn.cyj.springframework.beans.BeansException;
import cn.cyj.springframework.beans.factory.ConfigurableListableBeanFactory;
import cn.cyj.springframework.beans.factory.config.BeanFactoryPostProcessor;
import cn.cyj.springframework.beans.factory.config.BeanPostProcessor;
import cn.cyj.springframework.context.ApplicationEvent;
import cn.cyj.springframework.context.ApplicationListener;
import cn.cyj.springframework.context.ConfigurableApplicationContext;
import cn.cyj.springframework.context.event.ApplicationEventMulticaster;
import cn.cyj.springframework.context.event.ContextClosedEvent;
import cn.cyj.springframework.context.event.ContextRefreshedEvent;
import cn.cyj.springframework.context.event.SimpleApplicationEventMulticaster;
import cn.cyj.springframework.core.io.DefaultResourceLoader;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

// 重要方法：
// 1.实现ListableBeanFactory中的getBeansOfType(Class<T> type)
// 2.实现ListableBeanFactory中的getBeanDefinitionNames()
// 1.定义抽象方法refreshBeanFactory()，交由AbstractRefreshableApplicationContext实现
// 2.定义抽象方法closeBeanFactory()，交由AbstractRefreshableApplicationContext实现
// 3.定义抽象方法getBeanFactory()，交由AbstractRefreshableApplicationContext实现
// 疑问：
// 1.为什么AbstractApplicationContext继承DefaultResourceLoader？
// 因为继承DefaultResourceLoader后可以加载spring.xml
// 2.为什么BeanFactoryPostProcessor不像BeanPostProcessor先注册，而是直接调用？
// 因为BeanFactoryPostProcessor只在AbstractApplicationContext#refresh()中调用一次，而BeanPostProcessor是针对每个Bean生效
public abstract class AbstractApplicationContext extends DefaultResourceLoader
        implements ConfigurableApplicationContext {

    private ApplicationEventMulticaster applicationEventMulticaster;

    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

    @Override
    public void refresh() throws BeansException, IllegalStateException {
        // 1.Prepare this context for refreshing.
        prepareRefresh();

        // 2.创建BeanFactory并加载BeanDefinition
        ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();

        // 3.Prepare the bean factory for use in this context.
        prepareBeanFactory(beanFactory);

        // 4.Allows post-processing of the bean factory in context subclasses.
//        postProcessBeanFactory(beanFactory);

//        StartupStep beanPostProcess = this.applicationStartup.start("spring.context.beans.post-process");
        // 5.在实例化之前调用所有已注册的BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessors(beanFactory);

        // 6.在实例化之前注册BeanPostProcessors
        registerBeanPostProcessors(beanFactory);
//        beanPostProcess.end();

        // 7.Initialize message source for this context.
//        initMessageSource();

        // 8.初始化ApplicationEventMulticaster
        initApplicationEventMulticaster();

        // 9.Initialize other special beans in specific context subclasses.
//        onRefresh();

        // 10.注册ApplicationListener
        registerListeners();

        // 11.实例化所有非懒加载的单例Bean对象
        finishBeanFactoryInitialization(beanFactory);

        // 12.Last step: publish corresponding event.
        finishRefresh();
    }

    protected void prepareRefresh() {}

    protected ConfigurableListableBeanFactory obtainFreshBeanFactory() {
        refreshBeanFactory();
        return getBeanFactory();
    }

    protected void prepareBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        // 注册ApplicationContextAwareProcessor，实现ApplicationContextAware的功能
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));
    }

    protected void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor processor : beanFactoryPostProcessorMap.values()) {
            processor.postProcessBeanFactory(beanFactory);
        }
    }

    protected void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor processor: beanPostProcessorMap.values()) {
            beanFactory.addBeanPostProcessor(processor);
        }
    }

    protected void initApplicationEventMulticaster() {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        this.applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, this.applicationEventMulticaster);
    }

    protected void registerListeners() {
        Collection<ApplicationListener> applicationListeners = getBeansOfType(ApplicationListener.class).values();
        for (ApplicationListener listener : applicationListeners) {
            this.applicationEventMulticaster.addApplicationListener(listener);
        }
    }

    protected void finishBeanFactoryInitialization(ConfigurableListableBeanFactory beanFactory) {
        beanFactory.preInstantiateSingletons();
    }

    protected void finishRefresh() {
        // 发布容器refresh完毕事件
        publishEvent(new ContextRefreshedEvent(this));
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return getBeanFactory().getBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(name, requiredType);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void close() {
        // 发布关闭容器事件
        publishEvent(new ContextClosedEvent(this));

        // spring源码中destroySingletons()是定义在ConfigurableBeanFactory接口中
        getBeanFactory().destroySingletons();
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        getApplicationEventMulticaster().multicastEvent(event);
    }

    ApplicationEventMulticaster getApplicationEventMulticaster() throws IllegalStateException {
        if (this.applicationEventMulticaster == null) {
            throw new IllegalStateException("ApplicationEventMulticaster not initialized - " +
                    "call 'refresh' before multicasting events via the context: " + this);
        }
        return this.applicationEventMulticaster;
    }

    protected abstract void refreshBeanFactory() throws BeansException, IllegalStateException;

    protected abstract void closeBeanFactory();

    public abstract ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;
}
