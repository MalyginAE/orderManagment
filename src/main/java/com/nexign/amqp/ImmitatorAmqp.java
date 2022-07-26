package com.nexign.amqp;

import com.nexign.constants.process.variables.OrderContextConstants;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ImmitatorAmqp {
    private final RuntimeService service;

    @GetMapping("/asdf")
    public void startProcess() {
        service.createMessageCorrelation("").
                processInstanceId("").
                setVariable(OrderContextConstants.CALLBACK_OBJECT, "").
                correlateWithResult();
    }
}
