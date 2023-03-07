package cn.cyj.springframework.context.support;

import cn.cyj.springframework.beans.factory.BeanNameAware;

public abstract class AbstractRefreshableConfigApplicationContext extends AbstractRefreshableApplicationContext
        implements BeanNameAware {

    @Override
    public void setBeanName(String name) {

    }
}
