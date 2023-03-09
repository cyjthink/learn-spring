package cn.cyj.springframework.test.listener;

import cn.cyj.springframework.context.ApplicationListener;
import cn.cyj.springframework.test.event.RegisterSuccessEvent;

public class RegisterSuccessListener implements ApplicationListener<RegisterSuccessEvent> {

    @Override
    public void onApplicationEvent(RegisterSuccessEvent event) {
        System.out.println(">>RegisterSuccessListener监听到RegisterSuccessEvent事件, postUID=" + event.getPostUID() + ", postTime=" + event.getPostTime());
    }
}
