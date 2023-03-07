package cn.cyj.springframework.beans.factory;

public interface InitializingBean {

    void afterPropertiesSet() throws Exception;
}
