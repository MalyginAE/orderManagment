package com.nexign.services.promocode_inventory;

import com.nexign.constants.urls.RequestUrl;
import com.nexign.dto.order.context.MultisubscriptionOrderParameters;
import com.nexign.dto.order.context.MultisubscriptionRelatedParties;
import com.nexign.dto.order.context.PromoCodeDataModel;
import com.nexign.dto.promocode_inventory.dto.PromoCodeActivateResponseDto;
import com.nexign.dto.promocode_inventory.dto.PromoCodeInventoryActivateRequestBodyDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.function.Supplier;

import static com.nexign.constants.urls.RequestUrl.PROMOCODE_INVENTORY_ACTIVATE;


@Service
@Slf4j
public record ActivatePromoCodeService(WebClient webClient,RequestUrl requestUrl) {

    public PromoCodeActivateResponseDto requestToActivate(MultisubscriptionOrderParameters parameters) {
        log.info("requestToActivatePromoCode invoked");
        return prepareRequest(prepareRequestBody(parameters)).block();
    }


    private PromoCodeInventoryActivateRequestBodyDto prepareRequestBody(MultisubscriptionOrderParameters parameters) {
        PromoCodeDataModel promoCodeDataModel = parameters.getPromoCodeData();
        MultisubscriptionRelatedParties relatedParties = parameters.getRelatedParties();
        return new PromoCodeInventoryActivateRequestBodyDto(
                promoCodeDataModel.getRefName(),
                promoCodeDataModel.getValue().getValue(),
                relatedParties.getContactMsisdn()
        );
    }


    private Mono<PromoCodeActivateResponseDto> prepareRequest(PromoCodeInventoryActivateRequestBodyDto model) {

        return webClient.post().
                uri(requestUrl.getPromoCodeUrl(PROMOCODE_INVENTORY_ACTIVATE)).
                accept(MediaType.APPLICATION_JSON).
                bodyValue(model).
                exchangeToMono(response -> {
                    System.out.println(response.statusCode());
                    if (response.statusCode().equals(HttpStatus.OK)) {
                        return response.bodyToMono(PromoCodeActivateResponseDto.class);
                    } else {
                        return Mono.error((Supplier<? extends Throwable>) response.createException());
                    }
                })
                .retryWhen(Retry.fixedDelay(4, Duration.ofSeconds(5)));
    }
}
