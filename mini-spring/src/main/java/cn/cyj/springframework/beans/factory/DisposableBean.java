package cn.cyj.springframework.beans.factory;

// bean销毁时调用的接口，等同于配置在xml中的destroy-method
public interface DisposableBean {

    void destroy() throws Exception;
}
