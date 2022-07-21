package com.nexign.dto.promocode_inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromoCodeInventoryBookingRequestBodyDto {
    private String relatedPromoActionId;
    private String value;
    private Integer msisdn;
}
