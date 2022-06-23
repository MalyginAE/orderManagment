package com.nexign.javadelegate.realisation.servicetask;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class TestSetVariable implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("pIId = " + delegateExecution.getProcessInstanceId());
    }
}
