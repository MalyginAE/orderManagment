package com.nexign.amqp;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AmqpSender {
    @Autowired
    protected RabbitTemplate rabbitTemplate;

  //  @EventListener(ApplicationReadyEvent.class)
    public void send(String id){
        String exchange = "bss";
        String routingKey = "BSS";
        rabbitTemplate.convertAndSend(routingKey,id);
    }

}
