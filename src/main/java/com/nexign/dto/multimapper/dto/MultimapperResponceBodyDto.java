package com.nexign.dto.multimapper.dto;

import com.nexign.dto.order.context.RelatedItem;

import java.util.List;

public class MultimapperResponceBodyDto {
    ConversionResult conversionResult;
    TotalResult totalResult;
    String timestamp;
}




class ConversionResult {
    List<ConvertedItem> convertedItems;
    List<Context> context;
    String conversionType;
}


class TotalResult {
    String description;
    String code;
}

class ConvertedItem {
    ItemsResult itemsResult;
    List<OutputItem> outputItems;
    List<InputItem> inputItems;
}


class ItemsResult {
    List<UnresolvedCondition> unresolvedConditions;
    String description;
    String code;
}


class OutputItem {
    String itemType;
    String itemProvider;
    String name;
    String refId;
}


class InputItem {
    List<AdditionalInfo> additionalInfo;
    List<RelatedItem> relatedItems;
    List<Characteristic> characteristics;
    String itemType;
    String itemProvider;
    String name;
    String refId;
}


class UnresolvedCondition {}