package com.nexign.dto.order.context;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
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
class MultisubscriptionCharacteristic {
    String name;
    String type;
    String value;
    String characteristicUseId;
    String characteristicSpecValueId;
    String characteristicId;
    String isResourceValue;
}

