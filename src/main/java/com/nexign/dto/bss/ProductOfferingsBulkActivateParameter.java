package com.nexign.dto.bss;

import lombok.Value;

@Value
public class ProductOfferingsBulkActivateParameter {
    Long productOfferingId;
    ActionParameter actionParameters;
}
