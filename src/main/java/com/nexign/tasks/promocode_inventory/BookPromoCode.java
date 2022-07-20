package com.nexign.tasks.promocode_inventory;

import com.nexign.dto.order.context.PromoCodeDataModel;
import com.nexign.dto.promocode_inventory.dto.PromoCodeBookingRespoonceDto;
import com.nexign.exeptions.ServiceRetryExeption;
import com.nexign.helpers.AbstractDelegate;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.io.FileNotFoundException;
import java.time.Duration;
import java.util.function.Function;

@Component("BookPromoCode")
public class BookPromoCode extends AbstractDelegate {
    @Autowired
    WebClient webClient;

    @Override
    public void run(DelegateExecution delegateExecution) {
        PromoCodeBookingRespoonceDto promoCodeBookingRespoonceDto = requestToBooking();
        delegateExecution.setVariable("promoanswer",promoCodeBookingRespoonceDto);
    }

    private PromoCodeBookingRespoonceDto requestToBooking() {
        PromoCodeDataModel model = new PromoCodeDataModel();

        return webClient.post().
                uri(getUri()).
                bodyValue(model).
                retrieve()
                .onStatus(HttpStatus::is5xxServerError, ClientResponse::createException)
                .bodyToMono(PromoCodeBookingRespoonceDto.class).
                retryWhen(Retry.fixedDelay(1, Duration.ofSeconds(5)))
                .block();


    }
    private String getUri(){
        return UriComponentsBuilder.fromUriString("http://localhost:3333").build().toUriString();
    }


}
