package cn.cyj.springframework.beans.factory;

// 获取bean在容器中的name的感知接口
public interface BeanNameAware extends Aware {

    void setBeanName(String name);
}
