package com.nexign.dto.order.context;

import lombok.Data;

@Data
public
class RelatedItem {
    String itemType;
    String itemProvider;
    String itemId;
}
