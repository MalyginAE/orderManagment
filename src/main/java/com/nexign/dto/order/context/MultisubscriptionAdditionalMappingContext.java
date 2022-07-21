package com.nexign.dto.order.context;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MultisubscriptionAdditionalMappingContext {
    String fabricRefId;
    String type;
    String fabricProductId;
    String fabricProductOfferingId;
    String swapFabricProductOfferingId;
}
