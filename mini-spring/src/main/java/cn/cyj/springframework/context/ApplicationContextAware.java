package cn.cyj.springframework.context;

import cn.cyj.springframework.beans.BeansException;
import cn.cyj.springframework.beans.factory.Aware;

// 获取ApplicationContext的感知接口
public interface ApplicationContextAware extends Aware {

    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
