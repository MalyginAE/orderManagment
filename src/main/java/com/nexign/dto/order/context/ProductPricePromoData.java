package com.nexign.dto.order.context;

import lombok.Data;
import lombok.Value;

@Value
public class ProductPricePromoData {
    String mainComponentOrderId;
    Object priceValue;
}
