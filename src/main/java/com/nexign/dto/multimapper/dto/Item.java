package com.nexign.dto.multimapper.dto;

import com.nexign.dto.order.context.RelatedItem;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class Item {
    String refId;
    String name;
    String itemProvider;
    String itemType;
    List<Characteristic> characteristics;
    List<RelatedItem> relatedItems;
    List<AdditionalInfo> additionalInfo;
}
