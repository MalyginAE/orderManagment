package com.nexign.amqp;

import com.nexign.constants.process.variables.OrderContextConstants;
import com.nexign.dto.common.InteractionResponseSm;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ImmitatorAmqp {
    private final RuntimeService service;

    @PostMapping("/recieve/amqp")
    public void startProcess(@RequestBody InteractionResponseSm amqpModel) {
        //"processId_messageCorrelation_action"
        String[] s = amqpModel.getCorrelationId().split("_");
        service.createMessageCorrelation(s[1]).
                processInstanceId(s[0]).
                setVariable(OrderContextConstants.CALLBACK_OBJECT, amqpModel).
                correlateWithResult();
    }
}
