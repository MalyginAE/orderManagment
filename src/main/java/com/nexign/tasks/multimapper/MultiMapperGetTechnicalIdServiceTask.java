package com.nexign.tasks.multimapper;

import com.nexign.constants.process.variables.OrderContextConstants;
import com.nexign.dto.multimapper.dto.ConvertedItem;
import com.nexign.dto.multimapper.dto.MultimapperResponseBodyDto;
import com.nexign.dto.multimapper.dto.OutputItem;
import com.nexign.dto.order.context.MultisubscriptionAdditionalMappingContext;
import com.nexign.dto.order.context.MultisubscriptionOrderParameters;
import com.nexign.helpers.AbstractDelegate;
import com.nexign.services.multimapper.MultiMapperGetTechnicalIdService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.nexign.constants.process.variables.OrderContextConstants.*;


@Component("MultiMapperGetTechnicalIdServiceTask")
public class MultiMapperGetTechnicalIdServiceTask extends AbstractDelegate {
    @Autowired
    MultiMapperGetTechnicalIdService multiMapperGetTechnicalIdService;

    @Override
    public void run(DelegateExecution delegateExecution) {
        MultimapperResponseBodyDto response = multiMapperGetTechnicalIdService.getResponseWithTechnicalIdFromMultiMapper(delegateExecution);
        multiMapperGetTechnicalIdService.writeTechnicalIdInContext(delegateExecution, response);
    }




}
