package com.nexign.utils.parsing;

import com.nexign.bss.ordering.rest.model.common.CommonOrder;
import com.nexign.bss.ordering.rest.model.common.OrderItem;
import com.nexign.dto.order.context.*;

import java.util.ArrayList;
import java.util.List;

public class ParseInputOrder implements Order{
    private CommonOrder inputOrder;

    public ParseInputOrder(CommonOrder inputOrder) {
        this.inputOrder = inputOrder;
    }



    @Override
    public List<MultisubscriptionComponentOrderParameter> prepareComponentOrderParameters() {
        List<MultisubscriptionComponentOrderParameter> componentOrderParameters = new ArrayList<>();
        List<OrderItem> orderItems = inputOrder.getOrderItems();


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
