package com.nexign.dto.order.context;

import lombok.Data;
import lombok.Value;

@Value
public class PromoCodeDataModel {
    String refName;
    PromoCodeDataValue value;
    String valueType;
}
