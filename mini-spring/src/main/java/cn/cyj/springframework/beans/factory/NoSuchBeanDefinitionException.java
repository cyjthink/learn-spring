package cn.cyj.springframework.beans.factory;

// 简化了继承结构
public class NoSuchBeanDefinitionException extends RuntimeException {

    public NoSuchBeanDefinitionException() {
    }

    public NoSuchBeanDefinitionException(String message) {
        super(message);
    }

    public NoSuchBeanDefinitionException(String message, Throwable cause) {
        super(message, cause);
    }
}
