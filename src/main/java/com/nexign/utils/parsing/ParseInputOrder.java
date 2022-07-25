package com.nexign.utils.parsing;

import com.nexign.bss.ordering.rest.model.common.CommonOrder;
import com.nexign.bss.ordering.rest.model.common.OrderItem;
import com.nexign.dto.order.context.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ParseInputOrder implements Order {
    private CommonOrder inputOrder;

    public ParseInputOrder(CommonOrder inputOrder) {
        this.inputOrder = inputOrder;
    }


    @Override
    public List<MultisubscriptionComponentOrderParameter> prepareComponentOrderParameters() {
        List<MultisubscriptionComponentOrderParameter> componentOrderParameters = new ArrayList<>();
        List<OrderItem> orderItems = inputOrder.getOrderItems();
        var item = new ArrayList<>();
        orderItems.forEach(x -> componentOrderParameters.add(new MultisubscriptionComponentOrderParameter(
                null,
                null,
                null,
                null,
                null,
                x.getEntityCatalogItem().getEntityCatalogItemId(), // productOfferingId
                null,
                null,
                null
        )));

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
//        List<OrderItem> orderItems = inputOrder.getOrderItems();
//        orderItems.stream().map(x -> x.getOrderItemCharacteristics()).filter(x -> {
//                x.stream().filter(it -> Objects.equals(it.getCode(), "POQ_PRICE")).map(item -> item.getValue()).toList();
//        }
//        );
//        List<RelatedItem> priceItems = new ArrayList();
//        String neededType = "RecurringCharge";
//        ///def orderItemCharacteristics = inputOrder.relatedOrderItems.orderItemCharacteristics.flatten()
//        var price_exists = relatedItemsData.any({it.code.contains(POQ_PRICE)})
//        if (price_exists) {
//            def parsedRelatedItems = relatedItemsData.flatten().findAll({it.code == POQ_PRICE}).find({it.value["priceType"].first() == neededType})
//            logger.debug("PARSEDRELATEDITEMS + ${parsedRelatedItems}")
//            // def parsedRelatedItems = relatedItemsData.findAll({ it.code == POQ_PRICE }).find({ it.value["priceType"].first() == neededType })
//            def itemId = parsedRelatedItems.value.id.get(0)
//            def priceAlterations_itemId = parsedRelatedItems.value.priceAlterations ?.id ?.flatten()[0]
//
//            priceItems.add(new RelatedItem(
//                    "ProductOfferingPrice",
//                    itemProvider:CATALOG,
//                    itemId:itemId
//            ))
//            if (priceAlterations_itemId != null) {
//                priceItems.add(new RelatedItem(
//                        itemType:"ProductOfferingPrice",
//                        itemProvider:CATALOG,
//                        itemId:priceAlterations_itemId
//                ))
//
//            }
//        }
//        return priceItems;

        return null;
    }
}
