package com.nexign.tasks.multimapper;

import com.nexign.dto.multimapper.dto.MultimapperConversionRequestBody;
import com.nexign.helpers.AbstractDelegate;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

public class MultiMapperGetTechnicalIdServiceTask extends AbstractDelegate {
    @Autowired
    WebClient webClient;
    @Override
    public void run(DelegateExecution delegateExecution) {
        webClient.post().uri(getUri()).bodyValue(new MultimapperConversionRequestBody());
    }

    private String getUri() {
        return null;
    }
}
