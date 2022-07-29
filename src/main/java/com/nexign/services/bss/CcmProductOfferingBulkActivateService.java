package com.nexign.services.bss;

import com.nexign.constants.urls.RequestUrl;
import com.nexign.dto.bss.*;
import com.nexign.dto.common.FabricActionMap;
import com.nexign.dto.order.context.MultisubscriptionOrderParameters;
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
import static com.nexign.constants.urls.RequestUrl.getBssCheckProductUrl;


@Service
@RequiredArgsConstructor
public class CcmProductOfferingBulkActivateService {
    private final WebClient webClient;
    private final RequestUrl requestUrl;


    public CcmProductOfferingBulcActivateResponceBodyDto createRequest(DelegateExecution execution) {
        String correlationId = String.format("%s_%s_%s", execution.getProcessInstanceId(), "startEvent", "activate");
        List<String> technicalId = (List<String>) execution.getVariable(BSS_TECHNICAL_ID);
        includeActionMapInContext(execution,correlationId);
        ProductOfferingBulkActivateRequestBodyDto bodyDto = prepareRequestBodyToActivate(technicalId);
        return prepareRequestToActivate(bodyDto, execution,correlationId).block();
    }

    private void includeActionMapInContext(DelegateExecution execution,String correlationId) {
        List<FabricActionMap> actionMap = (List<FabricActionMap>) execution.getVariable(BSS_ACTION_MAP);
        //actionMap = (actionMap == null) ? new ArrayList<>() : actionMap;
        List<String> technicalId = (List<String>) execution.getVariable(BSS_TECHNICAL_ID);

        for (String s : technicalId) {
            actionMap.add(new FabricActionMap(s, correlationId, "PENDING_ACTIVATE"));
        }
    }

    private Mono<CcmProductOfferingBulcActivateResponceBodyDto> prepareRequestToActivate(ProductOfferingBulkActivateRequestBodyDto model, DelegateExecution execution, String correlationId) {
        MultisubscriptionOrderParameters parameters = (MultisubscriptionOrderParameters) execution.getVariable(ORDER_PARAMETERS);
        return webClient.post().
                uri(requestUrl.getBssActivateUrl(parameters,correlationId)).
                accept(MediaType.APPLICATION_JSON).
                bodyValue(model).
                exchangeToMono(response -> {
                    System.out.println(response.statusCode());
                    if (response.statusCode().equals(HttpStatus.OK) || response.statusCode().equals(HttpStatus.ACCEPTED)) {
                        execution.setVariableLocal("httpAnswerCode", response.statusCode());
                        return response.bodyToMono(CcmProductOfferingBulcActivateResponceBodyDto.class);
                    } else {
                        return Mono.error((Supplier<? extends Throwable>) response.createException());
                    }
                })
                .retryWhen(Retry.fixedDelay(4, Duration.ofSeconds(5)));
    }


    private ProductOfferingBulkActivateRequestBodyDto prepareRequestBodyToActivate(List<String> bssTechnicalIds) {
        List<ProductOfferingsBulkActivateParameter> parameters = new ArrayList<>();
        bssTechnicalIds.forEach(bssTechnicalId ->
                parameters.add(new ProductOfferingsBulkActivateParameter(
                        Long.getLong(bssTechnicalId),
                        new ActionParameter(true)
                ))
        );
        return new ProductOfferingBulkActivateRequestBodyDto(parameters);
    }

    public Boolean isNeedToCheckOrder(DelegateExecution execution, CcmProductOfferingBulcActivateResponceBodyDto responceBodyDto) {
       return (responceBodyDto.getOrderId() == null &&
               (Integer) execution.getVariableLocal("httpAnswerCode") == 200);
    }

    public void checkOrder(DelegateExecution execution) {
        List<String> technicalId = (List<String>) execution.getVariable(BSS_TECHNICAL_ID);
        CcmProductOfferingCheackOrderRequestBodyDto bodyDto = prepareRequestBodyToCheckOrder(technicalId);
        prepareRequestToCheckOrder(bodyDto,execution);
    }

    private Mono<CcmProductOfferingBulcActivateResponceBodyDto> prepareRequestToCheckOrder(CcmProductOfferingCheackOrderRequestBodyDto model, DelegateExecution execution) {
        MultisubscriptionOrderParameters parameters = (MultisubscriptionOrderParameters) execution.getVariable(ORDER_PARAMETERS);
        return webClient.post().
                uri(getBssCheckProductUrl(parameters)).
                accept(MediaType.APPLICATION_JSON).
                bodyValue(model).
                exchangeToMono(response -> {
                    System.out.println(response.statusCode());
                    if (response.statusCode().equals(HttpStatus.OK) || response.statusCode().equals(HttpStatus.ACCEPTED)) {
                        return response.bodyToMono(CcmProductOfferingBulcActivateResponceBodyDto.class);
                    } else {
                        return Mono.error((Supplier<? extends Throwable>) response.createException());
                    }
                })
                .retryWhen(Retry.fixedDelay(4, Duration.ofSeconds(5)));
    }

    private CcmProductOfferingCheackOrderRequestBodyDto prepareRequestBodyToCheckOrder(List<String> techIds){
        return new CcmProductOfferingCheackOrderRequestBodyDto(techIds,List.of(1,2));
    }

    //если при сопоставлении ошибка выбрость свое кастомное исключение

    // TODO: 27.07.2022

    public void analyzeAnswer(DelegateExecution execution, CcmProductOfferingCheackOrderResponceBodyDto bodyDto){
        List<String> technicalId = (List<String>) execution.getVariable(BSS_TECHNICAL_ID);
        if (technicalId.size() == bodyDto.getListInfo().getCount()){
            //todo добавить id в контекстную мапу
        }
        else {
            throw new RuntimeException();//todo подумать и дописать
        }
    }
}
