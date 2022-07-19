package com.nexign.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;
@Data
@Builder
public class MultisubscriptionOrderParameters {
    String orderId;
    MultisubscriptionRelatedParties relatedParties;
    List<MultisubscriptionChannel> channels;
    List<MultisubscriptionOrderCharacteristic> orderCharacteristics;
    List<MultisubscriptionComponentOrderParameter> affectedComponentOrders;
    PromoCodeDataModel promoCodeData;
    List<RelatedItem> priceRelatedItems;
    ProductPricePromoData productPriceData;
    Map<String, List<MultisubscriptionAdditionalMappingContext>> contextMap;
}
@Data
class RelatedItem {
    String itemType;
    String itemProvider;
    String itemId;
}
@Data
class MultisubscriptionRelatedParties {
    String msisdn;
    String contactMsisdn;
    String cdiPartyId;
    String partyRoleId;
}
@Data
class MultisubscriptionChannel {
    String channelId;
    String name;
    String role;
    String type;
}
@Data
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
@Data
class PromoCodeDataModel{
    String refName;
    PromoCodeDataValue value;
    String valueType;
}
@Data
class PromoCodeDataValue{
    String relatedPromoActionId;
    String value;
}
@Data
class ProductPricePromoData{
    String mainComponentOrderId;
    Object priceValue;
}
@Data
class MultisubscriptionAdditionalMappingContext {
    String fabricRefId;
    String type;
    String fabricProductId;
    String fabricProductOfferingId;
    String swapFabricProductOfferingId;
}
@Data
class MultisubscriptionSpecification {
    String specificationId;
    String code;
    String type;
    String name;
    List<MultisubscriptionCharacteristic> characteristics;
    String referredType;
}
@Data
class MultisubscriptionInstance {
    String instanceId;
    String type;
    String name;
    List<MultisubscriptionCharacteristic> characteristics;
    String state;
    String externalState;
    String startDate;
    String terminationDate;
    Object entity;
}
@Data
class MultisubscriptionCharacteristic {
    String name;
    String type;
    String value;
    String characteristicUseId;
    String characteristicSpecValueId;
    String characteristicId;
    String isResourceValue;
}
@Data
class MultisubscriptionOrderCharacteristic {
    String space;
    String name;
    Object value;
}
@Data
class ProductInventoryProductItemProductOrderItemRelationship {
    String productOrderItemId;
    String relationshipType;
}