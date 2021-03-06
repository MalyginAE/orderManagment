package com.nexign.tasks.promocode_inventory;

import com.nexign.constants.process.variables.OrderContextConstants;
import com.nexign.dto.order.context.MultisubscriptionOrderParameters;
import com.nexign.helpers.AbstractDelegate;
import com.nexign.services.promocode_inventory.BookingPromoCodeService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("BookPromoCode")
@RequiredArgsConstructor
public class BookPromoCode extends AbstractDelegate {


   private final BookingPromoCodeService promoCodeService;

    @Override
    public void run(DelegateExecution delegateExecution) {
        MultisubscriptionOrderParameters parameters = (MultisubscriptionOrderParameters) delegateExecution.getVariable(OrderContextConstants.ORDER_PARAMETERS);

        promoCodeService.requestToBooking(parameters);
    }


}
