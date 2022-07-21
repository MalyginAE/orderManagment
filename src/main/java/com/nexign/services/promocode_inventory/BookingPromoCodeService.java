package com.nexign.services.promocode_inventory;

import com.nexign.dto.order.context.MultisubscriptionOrderParameters;
import com.nexign.dto.order.context.MultisubscriptionRelatedParties;
import com.nexign.dto.order.context.PromoCodeDataModel;
import com.nexign.dto.promocode_inventory.dto.PromoCodeBookingResponseDto;
import com.nexign.dto.promocode_inventory.dto.PromoCodeInventoryBookingRequestBodyDto;
import com.nexign.services.AbstractCommonServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.function.Supplier;

import static com.nexign.constants.urls.RequestUrl.PROMOCODE_INVENTORY_BOOKING;
import static com.nexign.constants.urls.RequestUrl.getPromoCodeUrl;

@Service
public class BookingPromoCodeService extends AbstractCommonServiceHelper {

    @Autowired
    WebClient webClient;

    public PromoCodeBookingResponseDto requestToBooking(MultisubscriptionOrderParameters parameters){
        PromoCodeDataModel promoCodeDataModel = parameters.getPromoCodeData();

        MultisubscriptionRelatedParties relatedParties = parameters.getRelatedParties();
        PromoCodeInventoryBookingRequestBodyDto bodyDto = new PromoCodeInventoryBookingRequestBodyDto(
                promoCodeDataModel.getRefName(),
                promoCodeDataModel.getValue().getValue(),
                relatedParties.getContactMsisdn()
        );
        return prepareRequest(bodyDto).block();
    }

    private Mono<PromoCodeBookingResponseDto> prepareRequest(PromoCodeInventoryBookingRequestBodyDto model) {
        return webClient.post().
                uri(getPromoCodeUrl(PROMOCODE_INVENTORY_BOOKING)).
                accept(MediaType.APPLICATION_JSON).
                bodyValue(model).
               // retrieve()
                exchangeToMono(response -> {
                    System.out.println(response.statusCode());
                    if (response.statusCode().equals(HttpStatus.OK)) {
                        return response.bodyToMono(PromoCodeBookingResponseDto.class);
                    }
//                    else if (response.statusCode().equals(HttpStatus.CREATED)){
//                        return new Mono<PromoCodeBookingRespoonceDto>();
//                    }
                   // else if (response.statusCode().is4xxClientError()) {
                        //return response.bodyToMono(ErrorContainer.class);
                  //  }
                    else {
                        return Mono.error((Supplier<? extends Throwable>) response.createException());
                    }
                })
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(5)));
                //.onStatus(x->x.value() == 203, Mono.error(new ServiceRetryExeption("asda",203))

               // .onStatus(HttpStatus::is5xxServerError, ClientResponse::createException)
                //.bodyToMono(PromoCodeBookingRespoonceDto.class).



    }



}
