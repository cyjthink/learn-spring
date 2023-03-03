package cn.cyj.springframework.context.support;

import cn.cyj.springframework.beans.factory.BeanNameAware;
import cn.cyj.springframework.beans.factory.InitializingBean;

public abstract class AbstractRefreshableConfigApplicationContext extends AbstractRefreshableApplicationContext
        implements BeanNameAware, InitializingBean {
}
