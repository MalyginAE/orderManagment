package com.nexign.dto.promocode_inventory.dto;

import lombok.Value;

@Value
public class PromoCodeInventoryActivateRequestBodyDto {
    String relatedPromoActionId;
    String value;
    Integer msisdn;
}
