package com.nexign.utils.parsing.multimapper;

import com.nexign.dto.multimapper.dto.*;
import com.nexign.dto.order.context.MultisubscriptionComponentOrderParameter;
import com.nexign.dto.order.context.MultisubscriptionOrderCharacteristic;
import com.nexign.dto.order.context.MultisubscriptionOrderParameters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PrepareRequestBodyUtil {
    public static MultimapperConversionRequestBody prepareRequestBody(MultisubscriptionOrderParameters parameters) {
        MultimapperConversionRequestBody requestBody = new MultimapperConversionRequestBody(
                "MIDDLE_TO_FABRICS",
                getContextParameters(parameters),
                getItemsFromParameters(parameters)
        );
        return requestBody;
    }

    private static List<Item> getItemsFromParameters(MultisubscriptionOrderParameters parameters) {
        List<Item> itemList = new ArrayList<>();
        for (MultisubscriptionComponentOrderParameter p : parameters.getAffectedComponentOrders()) {
            itemList.add(new Item(
                    p.getProductOfferingId(),
                    p.getProductOfferingName(),
                    "CATALOG",
                    "PRODUCT_ENTITY",
                    new ArrayList<Characteristic>(),
                    parameters.getPriceRelatedItems(),
                    p.getInstance().getExternalState() == null ? Collections.emptyList() : getAdditionalInfo(p.getInstance().getExternalState())
                    ));
        }
        return itemList;
    }

    private static List<AdditionalInfo> getAdditionalInfo(String externalState) {
        List<AdditionalInfo> additionalInfoList = new ArrayList<>();
        additionalInfoList.add(new AdditionalInfo(
                "INVENTORY",
                "ProductStatus",
                externalState
        ));
        return additionalInfoList;
    }

    private static List<Context> getContextParameters(MultisubscriptionOrderParameters parameters) {
        List<Context> characteristicList = new ArrayList<>();
        for (MultisubscriptionOrderCharacteristic characteristic : parameters.getOrderCharacteristics()) {
            if (characteristic.getName().equals("segmentId") || characteristic.getName().equals("placeId") || characteristic.getName().equals("channelId")) {
                characteristicList.add(new Context(
                        characteristic.getSpace(),
                        characteristic.getName(),
                        characteristic.getValue()
                ));
            }
        }
        if (!parameters.getChannels().isEmpty()) {
            Context channel = new Context(
                    "CATALOG",
                    "channelId",
                    parameters.getChannels().get(0).getChannelId()
            );
            characteristicList.add(channel);
        }
        return characteristicList;
    }

}
