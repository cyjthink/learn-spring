package cn.cyj.springframework.beans.factory.config;

// spring中BeanReference是一个继承自BeanMetadataElement的接口
public class BeanReference {

    private final String beanName;

    public BeanReference(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }
}
