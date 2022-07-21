package com.nexign.services.multimapper;

import com.nexign.constants.process.variables.OrderContextConstants;
import com.nexign.dto.multimapper.dto.AdditionalInfo;
import com.nexign.dto.multimapper.dto.Characteristic;
import com.nexign.dto.multimapper.dto.Item;
import com.nexign.dto.multimapper.dto.MultimapperConversionRequestBody;
import com.nexign.dto.order.context.MultisubscriptionComponentOrderParameter;
import com.nexign.dto.order.context.MultisubscriptionOrderCharacteristic;
import com.nexign.dto.order.context.MultisubscriptionOrderParameters;
import com.nexign.services.AbstractCommonServiceHelper;
import com.nexign.utils.parsing.multimapper.PrepareRequestBodyUtil;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Service;
import com.nexign.dto.multimapper.dto.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MultiMapperGetTechnicalIdService extends AbstractCommonServiceHelper {


    public void getTechnicalIdAndIncludeInContext(DelegateExecution delegateExecution) {
        MultimapperConversionRequestBody multimapperConversionRequestBody = PrepareRequestBodyUtil.prepareRequestBody((MultisubscriptionOrderParameters) delegateExecution.getVariable(OrderContextConstants.ORDER_PARAMETERS));

    }




}
