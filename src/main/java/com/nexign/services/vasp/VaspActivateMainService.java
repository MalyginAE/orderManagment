package com.nexign.services.vasp;

import com.nexign.constants.process.variables.OrderContextConstants;
import com.nexign.constants.urls.RequestUrl;
import com.nexign.dto.common.FabricActionMap;
import com.nexign.dto.order.context.MultisubscriptionOrderParameters;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static com.nexign.constants.process.variables.ScenarioConstantCodes.VASP_SUCCESS_CODES;

@Service
@RequiredArgsConstructor
public class VaspActivateMainService {
    private final WebClient webClient;

    public void activateVaspProducts(DelegateExecution delegateExecution) {
        List<String> tecgnicalIds = (List<String>) delegateExecution.getVariable(OrderContextConstants.VASP_TECHNICAL_ID);
        List<FabricActionMap> actionMap = (List<FabricActionMap>) delegateExecution.getVariable(OrderContextConstants.VASP_ACTION_MAP);
        actionMap = (actionMap == null) ? new ArrayList<>() : actionMap; // todo возможно стоит перенести это в вызовв мм, и там объявить
        MultisubscriptionOrderParameters parameters = (MultisubscriptionOrderParameters) delegateExecution.getVariable(OrderContextConstants.ORDER_PARAMETERS);
        String msisdn = String.valueOf(parameters.getRelatedParties().getContactMsisdn());

        for (String id : tecgnicalIds) {
            actionMap.add(new FabricActionMap(id, id, "PENDING_ACTIVATE"));//todo возможно статусы нужно вынести в enum
            todoRequest(msisdn,id,actionMap);
        }


    }

    private void todoRequest(String msisdn, String technicalId,List<FabricActionMap> actionMap) {
        webClient.post().uri(RequestUrl.getVaspUrl("new", msisdn, technicalId)).retrieve()
                .onStatus(x -> !VASP_SUCCESS_CODES.contains(x.value()), ClientResponse::createException)
                .toBodilessEntity()
                .retryWhen(Retry.fixedDelay(4, Duration.ofSeconds(5))).block();
        actionMap.get(actionMap.size()-1).setStatus("ACTIVATE");
    }

}
