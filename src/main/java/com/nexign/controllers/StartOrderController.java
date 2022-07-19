package com.nexign.controllers;

//import com.nexign.amqp.AmqpSender;

import com.nexign.bss.ordering.rest.model.common.CommonOrder;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import javax.ws.rs.QueryParam;

@RestController
public class StartOrderController {
    @Autowired
    WebClient webClient;
    @Autowired
    private RuntimeService runtimeService;
//    @Autowired
//    private AmqpSender sender;
//    @GetMapping("put")
//    public ResponseEntity<Object> put(@QueryParam("id") String id){
//        sender.send(id);
//        return ResponseEntity.accepted().build();
//    }

    @GetMapping("/start")
    public ResponseEntity<Object> start(@QueryParam("id") String id){
        
        runtimeService.createMessageCorrelation("RabbitCallBack")
                //.processInstanceVariableEquals("123",id)
                .processInstanceId(id)
                .correlateWithResult();
        return ResponseEntity.accepted().build();
    }


    @PostMapping
    public ResponseEntity<Object> openApi(@RequestBody CommonOrder order){
        runtimeService.createMessageCorrelation("Message_2r730i1")
                .setVariable("inputOrder", order)
                .correlateWithResult();
        return ResponseEntity.accepted().build();
    }
}
