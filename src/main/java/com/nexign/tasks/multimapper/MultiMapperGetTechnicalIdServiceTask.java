package com.nexign.tasks.multimapper;

import com.nexign.dto.multimapper.dto.MultimapperResponseBodyDto;
import com.nexign.helpers.AbstractDelegate;
import com.nexign.services.multimapper.MultiMapperGetTechnicalIdService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("MultiMapperGetTechnicalIdServiceTask")
public class MultiMapperGetTechnicalIdServiceTask extends AbstractDelegate {
    @Autowired
    MultiMapperGetTechnicalIdService multiMapperGetTechnicalIdService;

    @Override
    public void run(DelegateExecution delegateExecution) {
        MultimapperResponseBodyDto response = multiMapperGetTechnicalIdService.getResponseWithTechnicalIdFromMultiMapper(delegateExecution);
        //multiMapperGetTechnicalIdService.writeTechnicalIdInContext(delegateExecution, response);
    }




}
