package com.nexign.dto.order.context;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

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


    public List<MultisubscriptionAdditionalMappingContext> getProductContextMap(String productOfferingId) {
        return contextMap.get(productOfferingId);
    }

    public void addedProviderInstanceIdInContextMap(String productOfferingId, String productId) {
        contextMap.forEach((k, v) -> {
            if (v.stream().anyMatch(it -> it.equals(productOfferingId))) {
                for (int i = 0; i < v.size(); i++) {
                    if (v.get(i).equals(productOfferingId)) {
                        v.get(i).fabricProductId = productId;
                    }
                }
            }
        });
    }

    MultisubscriptionComponentOrderParameter findComponentOrder(String technicalId) {
        String productOfferingId = "";
        for (Map.Entry<String, List<MultisubscriptionAdditionalMappingContext>> entry : contextMap.entrySet()) {
            if (entry.getValue().stream().anyMatch(it -> it.fabricProductOfferingId.equals(technicalId))){
                productOfferingId = entry.getKey();
                break;
            }
        }
        for (MultisubscriptionComponentOrderParameter componentOrder : affectedComponentOrders) {
            if (componentOrder.getProductOfferingId().equals(productOfferingId)){
                return componentOrder;
            }
        }
        return null;
    }
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

