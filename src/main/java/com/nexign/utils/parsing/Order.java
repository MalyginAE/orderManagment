package com.nexign.utils.parsing;

import com.nexign.dto.order.context.*;

import java.util.List;

public interface Order {
    List prepareComponentOrderParameters();

    MultisubscriptionRelatedParties getRelatedParty();

    List<MultisubscriptionOrderCharacteristic> getOrderCharacteristic();

    ProductPricePromoData getPriceData();


    List<ProductInventoryProductItemProductOrderItemRelationship> findRelationship();

    List<MultisubscriptionChannel> getMultisubscriptionChannels();

    PromoCodeDataModel getPromoCodeData();

    List<RelatedItem> getPriceRelatedItems();
}
