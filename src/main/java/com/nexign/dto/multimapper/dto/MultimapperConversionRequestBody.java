package com.nexign.dto.multimapper.dto;

import com.nexign.dto.order.context.RelatedItem;

import java.util.List;

public class MultimapperConversionRequestBody {
    String conversionType;
    List<Context> context;
    List<Item> items;
}
class Context {
    String space;
    String name;
    Object value;
}

class Item {
    String refId;
    String name;
    String itemProvider;
    String itemType;
    List<Characteristic> characteristics;
    List<RelatedItem> relatedItems;
    List<AdditionalInfo> additionalInfo;
}

class Characteristic {}

class AdditionalInfo {
    String space;
    String name;
    String value;
}