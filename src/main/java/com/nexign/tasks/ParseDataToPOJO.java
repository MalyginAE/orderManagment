package com.nexign.tasks;

import com.nexign.bss.ordering.rest.model.common.CommonOrder;
import com.nexign.helpers.AbstractDelegate;
import com.nexign.entieties.order.context.MultisubscriptionOrderParameters;
import com.nexign.utils.parsing.Order;
import com.nexign.utils.parsing.ParseInputOrder;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

import static com.nexign.constants.process.variables.OrderContextConstants.*;

@Component("parseInputOrderToPOJO")
public class ParseDataToPOJO extends AbstractDelegate {
    @Override
    public void run(DelegateExecution execution) {
        CommonOrder inputOrder = (CommonOrder) execution.getVariable(INPUT_ORDER);
        Order pojo = new ParseInputOrder();
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
        execution.setVariable(ORDER_PARAMETERS, orderParameters);
    }
}
