package com.nexign.services.ng_psi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexign.constants.process.variables.OrderContextConstants;
import com.nexign.dto.ng_psi.ProductInventoryActivationCompletedRequestBodyDto;
import com.nexign.dto.ng_psi.ProductInventoryActivationStartedPrepareRequestBodyDto;
import com.nexign.dto.ng_psi.ProductInventoryActivationStartedResponseBodyDto;
import com.nexign.dto.ng_psi.ProductInventoryEvent;
import com.nexign.dto.order.context.MultisubscriptionOrderParameters;
import com.nexign.utils.parsing.ng_psi.ProductInventoryPrepareRequestBodyListUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;


import java.time.Duration;
import java.util.function.Supplier;

import static com.nexign.constants.urls.RequestUrl.getProductInventoryUrl;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductInventoryActivationCompletedService {
    private final WebClient webClient;
    private final ObjectMapper mapper;

    @SneakyThrows
    public void sendActivationCompleted(DelegateExecution execution){
        MultisubscriptionOrderParameters parameters = (MultisubscriptionOrderParameters) execution.getVariable(OrderContextConstants.ORDER_PARAMETERS);
        ProductInventoryActivationCompletedRequestBodyDto productInventoryActivationCompletedRequestBodyDto = prepareRequestBody(parameters);
        log.info("invoked activation_compleated with body : {}", mapper.writeValueAsString(productInventoryActivationCompletedRequestBodyDto));
        ProductInventoryActivationStartedResponseBodyDto bodyDto = prepareRequestToActivationCompleated(productInventoryActivationCompletedRequestBodyDto).block();

    }

    private ProductInventoryActivationCompletedRequestBodyDto prepareRequestBody(MultisubscriptionOrderParameters parameters){
        return new ProductInventoryActivationCompletedRequestBodyDto(
                new ProductInventoryEvent(
                        parameters.getOrderId(),
                        "orderId",
                        parameters.getAffectedComponentOrders().stream().anyMatch(x -> x.getInstance().getExternalState().contains("TRIAL"))
                                ? "trial_activation_completed": "activation_completed",
                        "ORDER_MANAGEMENT"
                ),
                ProductInventoryPrepareRequestBodyListUtil.setListProducts(parameters)
        );
    }
        //todo вынести в утил для общих запросов в ng psi, часто будет повторяться
    private Mono<ProductInventoryActivationStartedResponseBodyDto> prepareRequestToActivationCompleated(ProductInventoryActivationCompletedRequestBodyDto model) {
        return webClient.post().
                uri(getProductInventoryUrl("event")).
                accept(MediaType.APPLICATION_JSON).
                bodyValue(model).
                exchangeToMono(response -> {
                    System.out.println(response.statusCode());
                    if (response.statusCode().equals(HttpStatus.OK)) {
                        return response.bodyToMono(ProductInventoryActivationStartedResponseBodyDto.class);
                    } else {
                        return Mono.error((Supplier<? extends Throwable>) response.createException());
                    }
                })
                .retryWhen(Retry.fixedDelay(4, Duration.ofSeconds(5)));
    }

}
