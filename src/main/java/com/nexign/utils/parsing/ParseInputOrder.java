package com.nexign.utils.parsing;

import com.nexign.entieties.order.context.*;

import java.util.List;

public class ParseInputOrder implements Order{



    @Override
    public List prepareComponentOrderParameters() {
        return null;
    }

    @Override
    public MultisubscriptionRelatedParties getRelatedParty() {
        return null;
    }

    @Override
    public List<MultisubscriptionOrderCharacteristic> getOrderCharacteristic() {
        return null;
    }

    @Override
    public ProductPricePromoData getPriceData() {
        return null;
    }

    @Override
    public List<ProductInventoryProductItemProductOrderItemRelationship> findRelationship() {
        return null;
    }

    @Override
    public List<MultisubscriptionChannel> getMultisubscriptionChannels() {
        return null;
    }

    @Override
    public PromoCodeDataModel getPromoCodeData() {
        return null;
    }

    @Override
    public List<RelatedItem> getPriceRelatedItems() {
        return null;
    }
}
