package com.nexign.services.promocode_inventory;

import com.nexign.dto.order.context.PromoCodeDataModel;
import com.nexign.dto.promocode_inventory.dto.PromoCodeBookingRespoonceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.time.Duration;

import static com.nexign.constants.urls.RequestUrl.PROMOCODE_INVENTORY_BOOKING;
import static com.nexign.constants.urls.RequestUrl.getPromoCodeUrl;

@Service
public class BookingPromoCodeService extends AbstractCommonServiceHelper {

    @Autowired
    WebClient webClient;

    public PromoCodeBookingRespoonceDto requestToBooking() {
        PromoCodeDataModel model = new PromoCodeDataModel();

        return webClient.post().
                uri(getPromoCodeUrl(PROMOCODE_INVENTORY_BOOKING)).
                bodyValue(model).
                retrieve()
                .onStatus(HttpStatus::is5xxServerError, ClientResponse::createException)
                .bodyToMono(PromoCodeBookingRespoonceDto.class).
                retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(5)))
                .block();


    }



}
