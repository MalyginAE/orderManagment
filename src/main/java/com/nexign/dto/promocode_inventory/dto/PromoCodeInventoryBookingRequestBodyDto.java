package com.nexign.dto.promocode_inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;


@Value
public class PromoCodeInventoryBookingRequestBodyDto {
    private String relatedPromoActionId;
    private String value;
    private Integer msisdn;
}
