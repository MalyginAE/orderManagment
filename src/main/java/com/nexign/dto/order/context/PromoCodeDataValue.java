package com.nexign.dto.order.context;

import lombok.Data;
import lombok.Value;

@Value
public class PromoCodeDataValue {
    String relatedPromoActionId;
    String value;
}
