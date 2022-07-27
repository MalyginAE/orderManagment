package com.nexign.services.multimapper;

import com.nexign.constants.process.variables.OrderContextConstants;
import com.nexign.dto.multimapper.dto.ConvertedItem;
import com.nexign.dto.multimapper.dto.MultimapperConversionRequestBody;
import com.nexign.dto.multimapper.dto.MultimapperResponseBodyDto;
import com.nexign.dto.multimapper.dto.OutputItem;
import com.nexign.dto.order.context.MultisubscriptionAdditionalMappingContext;
import com.nexign.dto.order.context.MultisubscriptionOrderParameters;
import com.nexign.utils.parsing.multimapper.PrepareRequestBodyUtil;
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
import java.util.Map;
import java.util.function.Supplier;

import static com.nexign.constants.process.variables.OrderContextConstants.*;
import static com.nexign.constants.urls.RequestUrl.getMultimapperUrl;

@Service
@RequiredArgsConstructor
public class MultiMapperGetTechnicalIdService {

    private final WebClient webClient;

    public MultimapperResponseBodyDto getResponseWithTechnicalIdFromMultiMapper(DelegateExecution delegateExecution) {
        MultimapperConversionRequestBody multimapperConversionRequestBody = PrepareRequestBodyUtil.prepareRequestBody((MultisubscriptionOrderParameters) delegateExecution.getVariable(OrderContextConstants.ORDER_PARAMETERS));
       return prepareRequest(multimapperConversionRequestBody).block();
    }

    private Mono<MultimapperResponseBodyDto> prepareRequest(MultimapperConversionRequestBody model) {
        return webClient.post().
                uri(getMultimapperUrl()).
                accept(MediaType.APPLICATION_JSON).
                bodyValue(model).
                exchangeToMono(response -> {
                    System.out.println(response.statusCode());
                    if (response.statusCode().equals(HttpStatus.OK)) {
                        return response.bodyToMono(MultimapperResponseBodyDto.class);
                    } else {
                        return Mono.error((Supplier<? extends Throwable>) response.createException());
                    }
                })
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(5)));
    }



    public void writeTechnicalIdInContext(DelegateExecution delegateExecution, MultimapperResponseBodyDto response) {
        if (response.getTotalResult().getCode().equals("0")) {
            MultisubscriptionOrderParameters parameters = (MultisubscriptionOrderParameters) delegateExecution.getVariable(ORDER_PARAMETERS);
            if (parameters.getContextMap().values().stream().allMatch(List::isEmpty)) { // если инстанцирование id ранее не проходило
                setAdditionalContext(response.getConversionResult().getConvertedItems(), parameters);
                setListTechnicalId(delegateExecution, parameters.getContextMap());
            }
        }



    }

    private static void setListTechnicalId(DelegateExecution delegateExecution, Map<String, List<MultisubscriptionAdditionalMappingContext>> map) {
        List<String> bssTechnicalId = new ArrayList<>();
        List<String> psTechnicalId = new ArrayList<>();
        List<String> vaspTechnicalId = new ArrayList<>();
        List<String> bssChargingTechnicalId = new ArrayList<>();
        map.forEach((k, v) -> {
            v.forEach(mappingContext -> {
                if (mappingContext.getType().equalsIgnoreCase("additional")) {
                    if (mappingContext.getFabricRefId().equals("VASP_PARTNER")) {
                        vaspTechnicalId.add(mappingContext.getFabricProductOfferingId());
                    }
                    if (mappingContext.getFabricRefId().equals("FABRIC_BSS")) {
                        bssTechnicalId.add(mappingContext.getFabricProductOfferingId());
                    }
                    if (mappingContext.getFabricRefId().equals("PARTNERS_SERVICE")) {
                        psTechnicalId.add(k);
                    }
                    if (mappingContext.getFabricRefId().equals("BSS_CHARGING")) {
                        bssChargingTechnicalId.add(k);
                    }
                }
            });
        });
        delegateExecution.setVariable(BSS_TECHNICAL_ID,bssTechnicalId);
        delegateExecution.setVariable(PS_TECHNICAL_ID,psTechnicalId);
        delegateExecution.setVariable(VASP_TECHNICAL_ID,vaspTechnicalId);
        delegateExecution.setVariable(BSS_CHARGING_TECHNICAL_ID,bssChargingTechnicalId);

    }

    private static void setAdditionalContext(List<ConvertedItem> list, MultisubscriptionOrderParameters parameters) {
        parameters.getContextMap().forEach((k, v) -> {
            List<OutputItem> outputItems = list.stream().filter(it -> {
                return it.getInputItems().stream().anyMatch(x -> {
                    return x.getRefId().equals(k);
                });
            }).findFirst().get().getOutputItems();

            outputItems.stream()
                    .filter(it -> !it.getItemProvider().equals("PARTNER_SERVICE_IVI"))
                    .forEach(i -> {
                        v.add(new MultisubscriptionAdditionalMappingContext(
                                i.getItemProvider(),
                                parameters.getAffectedComponentOrders().stream().filter(it -> it.getProductOfferingId().equals(k)).findFirst().get().getType(),
                                null,
                                i.getRefId(),
                                null
                        ));
                    });

        });
    }


}
