package com.nexign.dto.order.context;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
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
