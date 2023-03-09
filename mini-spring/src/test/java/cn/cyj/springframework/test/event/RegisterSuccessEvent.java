package cn.cyj.springframework.test.event;

import cn.cyj.springframework.context.ApplicationEvent;

public class RegisterSuccessEvent extends ApplicationEvent {

    private String postUID;
    private String postTime;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public RegisterSuccessEvent(Object source) {
        super(source);
    }

    public RegisterSuccessEvent(Object source, String postUID, String postTime) {
        super(source);
        this.postUID = postUID;
        this.postTime = postTime;
    }

    public String getPostUID() {
        return postUID;
    }

    public void setPostUID(String postUID) {
        this.postUID = postUID;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }
}
