package com.nexign.tasks.promocode_inventory;

import com.nexign.constants.process.variables.OrderContextConstants;
import com.nexign.dto.order.context.MultisubscriptionOrderParameters;
import com.nexign.helpers.AbstractDelegate;
import com.nexign.services.promocode_inventory.ActivatePromoCodeService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ActivatePromoCodeServiceTask extends AbstractDelegate {

    private final ActivatePromoCodeService promoCodeService;

    @Override
    public void run(DelegateExecution delegateExecution) {
        MultisubscriptionOrderParameters parameters = (MultisubscriptionOrderParameters) delegateExecution.getVariable(OrderContextConstants.ORDER_PARAMETERS);
        promoCodeService.requestToActivate(parameters);


    }
}
