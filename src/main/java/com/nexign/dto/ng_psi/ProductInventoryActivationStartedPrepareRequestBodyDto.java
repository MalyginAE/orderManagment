package com.nexign.dto.ng_psi;

import com.nexign.dto.order.context.ProductInventoryProductItemProductOrderItemRelationship;
import com.nexign.dto.order.context.PromoCodeDataModel;
import lombok.Value;

import java.util.List;
@Value
public class ProductInventoryActivationStartedPrepareRequestBodyDto {
    ProductInventoryEvent event;
    ProductInventoryClientInfo clientInfo;
    List<ProductInventoryProductItem> productOrderItems;
}

