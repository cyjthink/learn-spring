package cn.cyj.springframework.context;

public interface ApplicationEventPublisher {

    void publishEvent(ApplicationEvent event);
}
