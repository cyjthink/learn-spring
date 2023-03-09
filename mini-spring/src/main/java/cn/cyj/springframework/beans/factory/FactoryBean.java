package cn.cyj.springframework.beans.factory;

// FactoryBean提供了扩展的支持
// todo 条件：普通类:Student、FactoryBean：StudentFactoryBean<Student>、Student未在xml中声明
// todo 实现目标：调用context.getBean(Student.class)返回StudentFactoryBean<Student>#getObject()
// todo 逻辑基础：当发现Student没有在xml中声明时，会从已定义的BeanDefinition中寻找FactoryBean类型的对象。
//  找到传入FactoryBean<T>中的T为Student的StudentFactoryBean

// 当前的逻辑：
// 1.创建时将FactoryBean当成正常的对象创建，不区分FactoryBean和普通Bean
// 2.getBean()时判断是否为FactoryBean，如果是则返回FactoryBean#getObject()；否则返回FactoryBean
public interface FactoryBean<T> {

    T getObject() throws Exception;

    Class<?> getObjectType();

    boolean isSingleton();
}
