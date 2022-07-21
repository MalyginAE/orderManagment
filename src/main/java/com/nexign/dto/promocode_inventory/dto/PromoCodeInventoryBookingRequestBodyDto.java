package com.nexign.dto.promocode_inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class PromoCodeInventoryBookingRequestBodyDto {
    private String relatedPromoActionId;
    private String value;
    private Integer msisdn;
}
