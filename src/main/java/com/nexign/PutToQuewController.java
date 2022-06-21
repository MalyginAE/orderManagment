package com.nexign;

import com.nexign.amqp.AmqpSender;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.QueryParam;

@RestController
public class PutToQuewController {
    @Autowired
    RuntimeService runtimeService;
    @Autowired
    AmqpSender sender;
    @GetMapping("put")
    public ResponseEntity<Object> put(@QueryParam("id") String id){
        sender.send( id);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/start")
    public ResponseEntity<Object> start(@QueryParam("id") String id){
        runtimeService.createMessageCorrelation("RabbitCallBack")
                //.processInstanceVariableEquals("123",id)
                .processInstanceId(id)
                .correlateWithResult();
        return ResponseEntity.accepted().build();
    }
}
