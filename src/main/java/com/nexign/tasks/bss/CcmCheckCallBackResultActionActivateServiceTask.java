package com.nexign.tasks.bss;

import com.nexign.helpers.AbstractDelegate;
import com.nexign.services.bss.CcmCheckCallBackResultActionActivateService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CcmCheckCallBackResultActionActivateServiceTask extends AbstractDelegate {
    private final CcmCheckCallBackResultActionActivateService service;

    @Override
    public void run(DelegateExecution delegateExecution) {
        service.analyzeCallBack(delegateExecution);
    }
}

