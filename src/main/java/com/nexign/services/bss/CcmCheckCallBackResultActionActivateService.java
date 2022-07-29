package com.nexign.services.bss;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexign.constants.process.variables.OrderContextConstants;
import com.nexign.dto.bss.CcmCustomerOrderCallback;
import com.nexign.dto.common.InteractionResponseSm;
import com.nexign.dto.order.context.MultisubscriptionOrderParameters;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CcmCheckCallBackResultActionActivateService {
    private final ObjectMapper mapper ;

    @SneakyThrows
    public void analyzeCallBack(DelegateExecution delegateExecution) {
        MultisubscriptionOrderParameters parameters = (MultisubscriptionOrderParameters) delegateExecution.getVariable(OrderContextConstants.ORDER_PARAMETERS);
        InteractionResponseSm callbackObj = (InteractionResponseSm) delegateExecution.getVariable(OrderContextConstants.CALLBACK_OBJECT);

        if (callbackObj != null) {
            if (callbackObj.getStepStatus().equals("0")) {
                List<String> bssTechnicalId = (List<String>) delegateExecution.getVariable(OrderContextConstants.BSS_TECHNICAL_ID);
                CcmCustomerOrderCallback callback = mapper.readValue(mapper.writeValueAsString(callbackObj.getBody()), CcmCustomerOrderCallback.class);
                callback.getOrderEntities().forEach(orderEntity -> {
                    if (bssTechnicalId.stream().anyMatch(it -> it.equals(orderEntity.getProductOfferingId()))
                            && orderEntity.getAction().toLowerCase().equals("add")) {
                        parameters.addedProviderInstanceIdInContextMap(orderEntity.getProductOfferingId().toString(),orderEntity.getProductId().toString())
                        //todo добавиь в контекстную мапу
                    }
                });
            } else {
                throw new RuntimeException("body step status equals not 0");//todo написать свое
            }
        } else throw new RuntimeException("callback obj equals null");
    }
}
