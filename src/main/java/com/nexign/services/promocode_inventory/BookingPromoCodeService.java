package com.nexign.services.promocode_inventory;

import com.nexign.constants.urls.RequestUrl;
import com.nexign.dto.order.context.MultisubscriptionOrderParameters;
import com.nexign.dto.order.context.MultisubscriptionRelatedParties;
import com.nexign.dto.order.context.PromoCodeDataModel;
import com.nexign.dto.promocode_inventory.dto.PromoCodeBookingResponseDto;
import com.nexign.dto.promocode_inventory.dto.PromoCodeInventoryBookingRequestBodyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.function.Supplier;

import static com.nexign.constants.urls.RequestUrl.PROMOCODE_INVENTORY_BOOKING;
@RequiredArgsConstructor
@Service
public class BookingPromoCodeService  {

    private final RequestUrl requestUrl;
    private final WebClient webClient;

    public PromoCodeBookingResponseDto requestToBooking(MultisubscriptionOrderParameters parameters){
        PromoCodeInventoryBookingRequestBodyDto bodyDto = prepareRequestBody(parameters);
        return prepareRequest(bodyDto).block();
    }

    private PromoCodeInventoryBookingRequestBodyDto prepareRequestBody(MultisubscriptionOrderParameters parameters) {
        PromoCodeDataModel promoCodeDataModel = parameters.getPromoCodeData();

        MultisubscriptionRelatedParties relatedParties = parameters.getRelatedParties();
        return new PromoCodeInventoryBookingRequestBodyDto(
                promoCodeDataModel.getRefName(),
                promoCodeDataModel.getValue().getValue(),
                relatedParties.getContactMsisdn()
        );
    }

    private Mono<PromoCodeBookingResponseDto> prepareRequest(PromoCodeInventoryBookingRequestBodyDto model) {
        return webClient.post().
                uri(requestUrl.getPromoCodeUrl(PROMOCODE_INVENTORY_BOOKING)).
                accept(MediaType.APPLICATION_JSON).
                bodyValue(model).
                exchangeToMono(response -> {
                    System.out.println(response.statusCode());
                    if (response.statusCode().equals(HttpStatus.OK)) {
                        return response.bodyToMono(PromoCodeBookingResponseDto.class);
                    }
                    else {
                        return Mono.error((Supplier<? extends Throwable>) response.createException());
                    }
                })
                .retryWhen(Retry.fixedDelay(4, Duration.ofSeconds(5)));
    }



}
