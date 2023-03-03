package cn.cyj.springframework.beans.factory;

import com.sun.istack.internal.Nullable;

// 简化了继承结构
public class BeanDefinitionStoreException extends RuntimeException {

    public BeanDefinitionStoreException(String msg, @Nullable Throwable cause) {
        super(msg, cause);
    }
}
