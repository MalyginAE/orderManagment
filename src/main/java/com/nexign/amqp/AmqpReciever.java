package com.nexign.amqp;

import org.camunda.bpm.engine.RuntimeService;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AmqpReciever {
    @Autowired
    private RuntimeService processEngine ;
    @RabbitListener(bindings = @QueueBinding( //
            value = @Queue(value = "BSS"), //
            exchange = @Exchange(value = "bss", type = "topic"), //
            key = "*"))
    public void callBackBSS(String callBack){
        System.out.println(callBack);
        processEngine.createMessageCorrelation("RabbitCallBack") //
                .processInstanceId(callBack) //
//                .setVariable(ProcessConstants.VAR_NAME_shipmentId, shipmentId) //
                .correlateWithResult();
        //RabbitCallBack
        System.out.println("пришел callBack :" + callBack);
    }
}
