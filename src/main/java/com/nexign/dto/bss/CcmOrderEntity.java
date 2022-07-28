package com.nexign.dto.bss;

import lombok.Value;

@Value
public class CcmOrderEntity {
    String action;
    Long productOfferingId;
    Long productId;
}
