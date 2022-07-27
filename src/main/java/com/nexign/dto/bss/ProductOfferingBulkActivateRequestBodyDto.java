package com.nexign.dto.bss;

import lombok.Value;

import java.util.List;
@Value
public class ProductOfferingBulkActivateRequestBodyDto {
    List<ProductOfferingsBulkActivateParameter> parameters;
}

