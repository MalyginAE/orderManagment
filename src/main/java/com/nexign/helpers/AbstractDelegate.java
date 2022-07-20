package com.nexign.helpers;

import com.nexign.dto.promocode_inventory.dto.PromoCodeBookingRespoonceDto;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.time.Duration;

@Slf4j
public abstract class AbstractDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) {
        try {
            log.trace("Delegate {} was called for process {}",
                    getClass().getSimpleName(),
                    delegateExecution.getProcessInstanceId());
            run(delegateExecution);
        } catch (Exception exception) {
            log.error("Error occurred", exception);
            executeHandling(delegateExecution, exception);
        }
    }

    public void executeHandling(DelegateExecution delegateExecution, Exception exception) {
        throw new BpmnError("PROCESS_ERROR", exception);
    }

    public abstract void run(DelegateExecution delegateExecution);


    public <T> T createRequest(HttpMethod method, String uri, Object body, T responseType){
      return (T) WebClient.create().method(method).
                uri(uri).
                bodyValue(body).
                retrieve()
                .onStatus(HttpStatus::is5xxServerError, ClientResponse::createException)
                .bodyToMono(responseType.getClass()).
                retryWhen(Retry.fixedDelay(1, Duration.ofSeconds(5)))
                .block();
    }

}