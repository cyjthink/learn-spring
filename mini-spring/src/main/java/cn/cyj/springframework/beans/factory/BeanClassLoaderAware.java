package cn.cyj.springframework.beans.factory;

// 获取ClassLoader的感知接口
public interface BeanClassLoaderAware extends Aware {

    void setBeanClassLoader(ClassLoader classLoader);
}
