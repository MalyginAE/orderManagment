package com.nexign.tasks.bss;

import com.nexign.dto.bss.CcmProductOfferingBulcActivateResponceBodyDto;
import com.nexign.helpers.AbstractDelegate;
import com.nexign.services.bss.CcmProductOfferingBulkActivateService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CcmProductOfferingBulkActivateServiceTask extends AbstractDelegate {
    private final CcmProductOfferingBulkActivateService bulkActivateService;
    @Override
    public void run(DelegateExecution delegateExecution) {
        CcmProductOfferingBulcActivateResponceBodyDto responceBodyDto = bulkActivateService.createRequest(delegateExecution);
        if (bulkActivateService.isNeedToCheckOrder(delegateExecution,responceBodyDto)) bulkActivateService.checkOrder(delegateExecution);
    }
}
