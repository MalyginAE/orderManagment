package com.nexign.services.bss;

import com.nexign.constants.process.variables.OrderContextConstants;
import com.nexign.dto.bss.CcmCustomerOrderCallback;
import com.nexign.dto.common.InteractionResponseSm;
import lombok.SneakyThrows;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Service;
import spinjar.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

@Service
public class CcmCheckCallBackResultActionActivateService {
    ObjectMapper mapper = new ObjectMapper();//todo внедрять как бин

    @SneakyThrows
    public void analyzeCallBack(DelegateExecution delegateExecution) {
        InteractionResponseSm callbackObj = (InteractionResponseSm) delegateExecution.getVariable(OrderContextConstants.CALLBACK_OBJECT);

        if (callbackObj != null) {
            if (callbackObj.getStepStatus().equals("0")) {
                List<String> bssTechnicalId = (List<String>) delegateExecution.getVariable(OrderContextConstants.BSS_TECHNICAL_ID);
                CcmCustomerOrderCallback callback = mapper.readValue(mapper.writeValueAsString(callbackObj.getBody()), CcmCustomerOrderCallback.class);
                callback.getOrderEntities().forEach(orderEntity -> {
                    if (bssTechnicalId.stream().anyMatch(it -> it.equals(orderEntity.getProductOfferingId()))
                            && orderEntity.getAction().toLowerCase().equals("add")) {
                        //todo добавиь в контекстную мапу
                    }
                });
            } else {
                throw new RuntimeException("body step status equals not 0");//todo написать свое
            }
        } else throw new RuntimeException("callback obj equals null");
    }
}
