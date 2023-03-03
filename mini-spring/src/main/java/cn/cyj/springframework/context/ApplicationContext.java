package cn.cyj.springframework.context;

import cn.cyj.springframework.beans.factory.HierarchicalBeanFactory;
import cn.cyj.springframework.beans.factory.ListableBeanFactory;
import cn.cyj.springframework.core.env.EnvironmentCapable;
import cn.cyj.springframework.core.io.support.ResourcePatternResolver;

// 这里还继承了HierarchicalBeanFactory，也是为了实现层级关系吗？
public interface ApplicationContext extends EnvironmentCapable, ListableBeanFactory, HierarchicalBeanFactory,
        MessageSource, ApplicationEventPublisher, ResourcePatternResolver {
}
