package cn.cyj.springframework.beans.factory;

// bean创建时调用的接口，等同于配置在xml中的init-method
public interface InitializingBean {

    void afterPropertiesSet() throws Exception;
}
