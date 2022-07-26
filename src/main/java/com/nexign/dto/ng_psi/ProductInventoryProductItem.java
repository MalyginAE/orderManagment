package com.nexign.dto.ng_psi;

import com.nexign.dto.order.context.ProductInventoryProductItemProductOrderItemRelationship;
import com.nexign.dto.order.context.PromoCodeDataModel;
import lombok.Value;

import java.util.List;
@Value
public class ProductInventoryProductItem {
    String productOrderItemId;
    String productId;
    boolean isBundle;
//    Object label;
//    String status;
    String productOfferingId;
    String productOfferingName;
    List<PromoCodeDataModel> characteristic;
//    List<ProductInventoryMetaInfo> metaInfo;
    Object productPrice;
    List<ProductInventoryFabricRef> fabricRef;
    List<ProductInventoryProductItemProductOrderItemRelationship> productOrderItemRelationship;
    List<ProductInventoryProductItemProductOrderItemRelationship> productRelationship;
}
