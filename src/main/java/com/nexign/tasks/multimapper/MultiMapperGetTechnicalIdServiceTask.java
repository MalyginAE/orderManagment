package com.nexign.tasks.multimapper;

import com.nexign.constants.urls.RequestUrl;
import com.nexign.dto.multimapper.dto.MultimapperConversionRequestBody;
import com.nexign.helpers.AbstractDelegate;
import com.nexign.services.multimapper.MultiMapperGetTechnicalIdService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;


@Component
public class MultiMapperGetTechnicalIdServiceTask extends AbstractDelegate {
    @Autowired
    MultiMapperGetTechnicalIdService multiMapperGetTechnicalIdService;
    @Override
    public void run(DelegateExecution delegateExecution) {

        multiMapperGetTechnicalIdService.getTechnicalIdAndIncludeInContext(delegateExecution);
    }


}
