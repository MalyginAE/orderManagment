package com.nexign.dto.ng_psi;

import lombok.Value;

import java.util.List;
@Value
public class ProductInventoryActivationStartedResponseBodyDto {
    String refId;
    List<ProductInventoryProduct> products;
}


