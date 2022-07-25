package com.nexign.dto.order.context;

import lombok.Data;
import lombok.Value;

@Value
public class RelatedItem {
    String itemType;
    String itemProvider;
    String itemId;
}
