package com.nexign.tasks.ng_psi;

import com.nexign.constants.process.variables.OrderContextConstants;
import com.nexign.dto.ng_psi.ProductInventoryActivationCompletedRequestBodyDto;
import com.nexign.dto.ng_psi.ProductInventoryEvent;
import com.nexign.dto.order.context.MultisubscriptionOrderParameters;
import com.nexign.helpers.AbstractDelegate;
import com.nexign.services.ng_psi.ProductInventoryActivationCompletedService;
import com.nexign.utils.parsing.ng_psi.ProductInventoryPrepareRequestBodyListUtil;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductInventoryActivationCompletedServiceTask extends AbstractDelegate {
    private final ProductInventoryActivationCompletedService activationCompletedService;
    @Override
    public void run(DelegateExecution delegateExecution) {

    }
}
