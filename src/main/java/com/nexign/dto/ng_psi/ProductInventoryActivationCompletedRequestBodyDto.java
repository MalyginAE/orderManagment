package com.nexign.dto.ng_psi;

import lombok.Value;

import java.util.List;

@Value
public class ProductInventoryActivationCompletedRequestBodyDto {
    ProductInventoryEvent event;
    List<ProductInventoryProductItem> products;
}
