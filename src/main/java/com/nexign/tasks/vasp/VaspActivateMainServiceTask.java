package com.nexign.tasks.vasp;

import com.nexign.helpers.AbstractDelegate;
import com.nexign.services.vasp.VaspActivateMainService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VaspActivateMainServiceTask extends AbstractDelegate {
    private final VaspActivateMainService service;
    @Override
    public void run(DelegateExecution delegateExecution) {
        service.activateVaspProducts(delegateExecution);
    }
}
