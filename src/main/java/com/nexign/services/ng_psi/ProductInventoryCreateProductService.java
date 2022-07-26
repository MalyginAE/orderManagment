package com.nexign.services.ng_psi;

import com.nexign.dto.ng_psi.*;
import com.nexign.dto.order.context.MultisubscriptionOrderParameters;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import static com.nexign.constants.urls.RequestUrl.getProductInventoryUrl;

@Service
@RequiredArgsConstructor
public class ProductInventoryCreateProductService {
    private final WebClient webClient;

    public ProductInventoryActivationStartedResponseBodyDto createActivationStartedRequest(MultisubscriptionOrderParameters parameters) {
        return prepareRequestToActivate(prepareRequestBodyActivationStarted(parameters)).block();
    }

    private ProductInventoryActivationStartedPrepareRequestBodyDto prepareRequestBodyActivationStarted(MultisubscriptionOrderParameters parameters) {
        return new ProductInventoryActivationStartedPrepareRequestBodyDto(
                new ProductInventoryEvent(
                        parameters.getOrderId(),
                        "orderId",
                        "activation_started",
                        "ORDER_MANAGEMENT"
                ),
                new ProductInventoryClientInfo(
                        parameters.getRelatedParties().getPartyRoleId(),
                        parameters.getRelatedParties().getCdiPartyId()
                ),
                getProductOrderItems(parameters)
        );
    }

    private List<ProductInventoryProductItem> getProductOrderItems(MultisubscriptionOrderParameters parameters) {
        List<ProductInventoryProductItem> productItemList = new ArrayList<>();
        parameters.getAffectedComponentOrders().forEach(component -> {
            productItemList.add(new ProductInventoryProductItem(
                    component.getOrderItemId(),
                    null,
                    component.getType().equals("bundle"),
                    component.getProductOfferingId(),
                    component.getProductOfferingName(),
                    (component.getType().equals("bundle") && parameters.getPromoCodeData() != null) ? Collections.singletonList(parameters.getPromoCodeData()) : null,
                    (parameters.getProductPriceData() != null && parameters.getProductPriceData().getMainComponentOrderId().equals(component.getComponentOrderId())) ?
                            parameters.getProductPriceData().getPriceValue() : null,
                    null,
                    component.getProductOrderItemRelationship(),
                    null
            ));
        });
        return productItemList;
    }

    private Mono<ProductInventoryActivationStartedResponseBodyDto> prepareRequestToActivate(ProductInventoryActivationStartedPrepareRequestBodyDto model) {
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
