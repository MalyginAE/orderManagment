package com.nexign.tasks;

import com.nexign.bss.ordering.rest.model.common.CommonOrder;
import com.nexign.helpers.AbstractDelegate;
import com.nexign.dto.order.context.MultisubscriptionOrderParameters;
import com.nexign.utils.parsing.Order;
import com.nexign.utils.parsing.ParseInputOrder;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

import static com.nexign.constants.process.variables.OrderContextConstants.*;

@Component("parseInputOrderToPOJO")
@Slf4j
public class ParseDataToPOJO extends AbstractDelegate {
    @Override
    public void run(DelegateExecution execution) {
        log.info("ParseDataToPOJO invoked ");
        CommonOrder inputOrder = (CommonOrder) execution.getVariable(INPUT_ORDER);
        Order pojo = new ParseInputOrder(inputOrder);
        MultisubscriptionOrderParameters orderParameters = MultisubscriptionOrderParameters.builder()
                .orderId(inputOrder.getCommonOrderId())
                .relatedParties(pojo.getRelatedParty())
                .orderCharacteristics(pojo.getOrderCharacteristic())
                .affectedComponentOrders(pojo.prepareComponentOrderParameters())
                .promoCodeData(pojo.getPromoCodeData())
                .productPriceData(pojo.getPriceData())
                .priceRelatedItems(pojo.getPriceRelatedItems())
                .channels(pojo.getMultisubscriptionChannels())
                .build();
        execution.setVariable(IS_PROMOCODE_EXISTS,pojo.getPromoCodeData() != null);
        execution.setVariable(ORDER_PARAMETERS, orderParameters);
    }
}
