package com.nexign.entieties.order.context;

import lombok.Data;

@Data
public
class PromoCodeDataModel {
    String refName;
    PromoCodeDataValue value;
    String valueType;
}
