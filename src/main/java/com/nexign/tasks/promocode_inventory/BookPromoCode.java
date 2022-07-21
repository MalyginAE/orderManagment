package com.nexign.tasks.promocode_inventory;

import com.nexign.dto.promocode_inventory.dto.PromoCodeBookingRespoonceDto;
import com.nexign.helpers.AbstractDelegate;
import com.nexign.services.promocode_inventory.BookingPromoCodeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("BookPromoCode")
public class BookPromoCode extends AbstractDelegate {

    @Autowired
   BookingPromoCodeService promoCodeService;

    @Override
    public void run(DelegateExecution delegateExecution) {
        PromoCodeBookingRespoonceDto promoCodeBookingRespoonceDto = promoCodeService.requestToBooking();
        delegateExecution.setVariable("promoanswer", promoCodeBookingRespoonceDto);
    }


}
