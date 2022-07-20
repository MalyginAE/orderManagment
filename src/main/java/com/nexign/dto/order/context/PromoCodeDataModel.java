package com.nexign.dto.order.context;

import lombok.Data;

@Data
public
class PromoCodeDataModel {
    String refName;
    PromoCodeDataValue value;
    String valueType;
}
