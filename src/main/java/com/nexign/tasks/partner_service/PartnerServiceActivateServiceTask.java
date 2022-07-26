package com.nexign.tasks.partner_service;

import com.nexign.helpers.AbstractDelegate;
import com.nexign.services.partner_service.PartnerServiceActivateService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PartnerServiceActivateServiceTask extends AbstractDelegate {
    private final PartnerServiceActivateService partnerServiceActivateService;
    @Override
    public void run(DelegateExecution delegateExecution) {
        partnerServiceActivateService.activatePsFabrics(delegateExecution);
    }
}
