package com.nexign.dto.order.context;

import lombok.Data;

import java.util.List;

@Data
public
class MultisubscriptionComponentOrderParameter {
    String componentOrderId;
    String orderItemId;
    String action;
    MultisubscriptionSpecification specification;
    MultisubscriptionInstance instance;
    String productOfferingId;
    String productOfferingName;
    String type;
    List<ProductInventoryProductItemProductOrderItemRelationship> productOrderItemRelationship;
}
