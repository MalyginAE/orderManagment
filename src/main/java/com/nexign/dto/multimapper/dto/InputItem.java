package com.nexign.dto.multimapper.dto;

import com.nexign.dto.order.context.RelatedItem;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class InputItem {
    List<AdditionalInfo> additionalInfo;
    List<RelatedItem> relatedItems;
    List<Characteristic> characteristics;
    String itemType;
    String itemProvider;
    String name;
    String refId;
}
