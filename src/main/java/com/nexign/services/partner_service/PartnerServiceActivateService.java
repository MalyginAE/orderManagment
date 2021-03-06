package com.nexign.services.partner_service;

import com.nexign.dto.common.FabricActionMap;
import com.nexign.dto.ng_psi.ProductInventoryActivationStartedPrepareRequestBodyDto;
import com.nexign.dto.ng_psi.ProductInventoryActivationStartedResponseBodyDto;
import com.nexign.dto.order.context.MultisubscriptionOrderParameters;
import com.nexign.dto.order.context.MultisubscriptionRelatedParties;
import com.nexign.dto.partner_service.*;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static com.nexign.constants.process.variables.OrderContextConstants.*;
import static com.nexign.constants.urls.RequestUrl.getPartnerServiceUrl;
import static com.nexign.constants.urls.RequestUrl.getProductInventoryUrl;

@Service
@RequiredArgsConstructor
public class PartnerServiceActivateService {
    private final WebClient webClient;

    public void activatePsFabrics(DelegateExecution execution) {
        List<PartnerServiceActivateRequestBodyDto> activateRequestBodyDto = prepareRequestBody(execution);

    }


    private Mono<PartnerServiceActivateResponseBodyDto> prepareRequestToActivate(ProductInventoryActivationStartedPrepareRequestBodyDto model) {
        return webClient.post().
                uri(getPartnerServiceUrl()).
                accept(MediaType.APPLICATION_JSON).
                bodyValue(model).
                exchangeToMono(response -> {
                    System.out.println(response.statusCode());
                    if (response.statusCode().equals(HttpStatus.OK)) {
                        return response.bodyToMono(PartnerServiceActivateResponseBodyDto.class);
                    } else {
                        return Mono.error((Supplier<? extends Throwable>) response.createException());
                    }
                })
                .retryWhen(Retry.fixedDelay(4, Duration.ofSeconds(5)));
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
