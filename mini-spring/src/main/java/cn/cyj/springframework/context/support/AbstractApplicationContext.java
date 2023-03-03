package cn.cyj.springframework.context.support;

import cn.cyj.springframework.beans.BeansException;
import cn.cyj.springframework.beans.factory.ConfigurableListableBeanFactory;
import cn.cyj.springframework.context.ConfigurableApplicationContext;
import cn.cyj.springframework.core.io.DefaultResourceLoader;

import java.io.IOException;
import java.util.Map;

// 重要方法：
// 1.实现ListableBeanFactory中的getBeansOfType(Class<T> type)
// 2.实现ListableBeanFactory中的getBeanDefinitionNames()
// 1.定义抽象方法refreshBeanFactory()，交由AbstractRefreshableApplicationContext实现
// 2.定义抽象方法closeBeanFactory()，交由AbstractRefreshableApplicationContext实现
// 3.定义抽象方法getBeanFactory()，交由AbstractRefreshableApplicationContext实现
public abstract class AbstractApplicationContext extends DefaultResourceLoader
        implements ConfigurableApplicationContext {

    @Override
    public void refresh() throws BeansException, IllegalStateException {
        // 1.Prepare this context for refreshing.
//        prepareRefresh();

        // 2.Tell the subclass to refresh the internal bean factory.
//        ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();

        // 3.Prepare the bean factory for use in this context.
//        prepareBeanFactory(beanFactory);

        // 4.Allows post-processing of the bean factory in context subclasses.
//        postProcessBeanFactory(beanFactory);

//        StartupStep beanPostProcess = this.applicationStartup.start("spring.context.beans.post-process");
        // 5.Invoke factory processors registered as beans in the context.
//        invokeBeanFactoryPostProcessors(beanFactory);

        // 6.Register bean processors that intercept bean creation.
//        registerBeanPostProcessors(beanFactory);
//        beanPostProcess.end();

        // 7.Initialize message source for this context.
//        initMessageSource();

        // 8.Initialize event multicaster for this context.
//        initApplicationEventMulticaster();

        // 9.Initialize other special beans in specific context subclasses.
//        onRefresh();

        // 10.Check for listener beans and register them.
//        registerListeners();

        // 11.Instantiate all remaining (non-lazy-init) singletons.
//        finishBeanFactoryInitialization(beanFactory);

        // 12.Last step: publish corresponding event.
//        finishRefresh();
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
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public void close() throws IOException {

    }

    protected abstract void refreshBeanFactory() throws BeansException, IllegalStateException;

    protected abstract void closeBeanFactory();

    public abstract ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;
}
