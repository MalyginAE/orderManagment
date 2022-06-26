package com.nexign.javadelegate.realisation.servicetask;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("throwsError")
public class ThrowsError implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("Hi");
        throw new Exception();
        //throw new BpmnError("1");
    }
}

/*
Если у нас вылетает не throw new BpmnError("1"), то движок пытается повторить действие от начала транзакции 3 раза, а так движок прервывает поток
 */
