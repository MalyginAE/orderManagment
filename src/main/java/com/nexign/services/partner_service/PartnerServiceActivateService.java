package com.nexign.services.partner_service;

import com.nexign.dto.common.FabricActionMap;
import com.nexign.dto.order.context.MultisubscriptionOrderParameters;
import com.nexign.dto.order.context.MultisubscriptionRelatedParties;
import com.nexign.dto.partner_service.ClientInfo;
import com.nexign.dto.partner_service.ExtraParams;
import com.nexign.dto.partner_service.Parameters;
import com.nexign.dto.partner_service.PartnerServiceActivateRequestBodyDto;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

import static com.nexign.constants.process.variables.OrderContextConstants.*;

@Service
@RequiredArgsConstructor
public class PartnerServiceActivateService {
    private final WebClient webClient;

    public void createActivationStartedRequest(DelegateExecution execution) {
        List<PartnerServiceActivateRequestBodyDto> activateRequestBodyDto = prepareRequestBody(execution);

    }

    private List<PartnerServiceActivateRequestBodyDto> prepareRequestBody(DelegateExecution execution) {
        MultisubscriptionOrderParameters parameters = (MultisubscriptionOrderParameters) execution.getVariable(ORDER_PARAMETERS);
        List<PartnerServiceActivateRequestBodyDto> activateRequestBodyDtoBulk = new ArrayList<>();
        List<FabricActionMap> actionMapList = new ArrayList<>();
        String correlationId = execution.getProcessInstanceId().concat("_activate");
        List<String> psTechnicalId = (List<String>) execution.getVariable(PS_TECHNICAL_ID);

        psTechnicalId.forEach(id -> {
            actionMapList.add(new FabricActionMap(id,correlationId,"PENDING_ACTIVATE"));
            activateRequestBodyDtoBulk.add(setBody(parameters.getRelatedParties(),id,correlationId,parameters.getChannels().get(0).getChannelId()));
        });

        execution.setVariable(PS_ACTION_MAP,actionMapList);

        return activateRequestBodyDtoBulk;
    }

    private static PartnerServiceActivateRequestBodyDto setBody(MultisubscriptionRelatedParties relatedParties, String fabricProductOfferingId, String correlationId, String channelId) {
        return new PartnerServiceActivateRequestBodyDto(
                "2.0",
                correlationId,
                "activate",
                new Parameters(
                        fabricProductOfferingId,
                        new ClientInfo(
                                relatedParties.getCdiPartyId(),
                                relatedParties.getPartyRoleId(),
                                relatedParties.getContactMsisdn().toString()
                        ),
                        channelId,
                        new ExtraParams()
                )
        );
    }


}
