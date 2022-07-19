package com.nexign.tasks;

import com.nexign.bss.ordering.rest.model.common.CommonOrder;
import com.nexign.models.MultisubscriptionOrderParameters;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import static com.nexign.constants.process.variables.OrderContextConstants.*;
@Component("parseInputOrderToPOJO")
public class ParseDataToPOJO implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        CommonOrder inputOrder = (CommonOrder) execution.getVariable(INPUT_ORDER);
        MultisubscriptionOrderParameters orderParameters = MultisubscriptionOrderParameters.builder()
                .orderId(inputOrder.getCommonOrderId())
                .build();
        execution.setVariable(ORDER_PARAMETERS,orderParameters);
    }
}
