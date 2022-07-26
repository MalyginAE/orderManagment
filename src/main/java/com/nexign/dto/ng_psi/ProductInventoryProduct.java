package com.nexign.dto.ng_psi;

import lombok.Value;

import java.util.List;
@Value
public class ProductInventoryProduct {
    String productOfferingId;
    String productOfferingName;
    List<ProductInventoryProductRelationship> productRelationship;
    String productId;
    String productOrderItemId;
    boolean isBundle;
    String Status;
}
