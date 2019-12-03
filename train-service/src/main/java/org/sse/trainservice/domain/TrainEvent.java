package org.sse.trainservice.domain;

import org.springframework.context.ApplicationEvent;

public class TrainEvent extends ApplicationEvent {
    public TrainEvent(Object source){
        super(source);
    }
}
