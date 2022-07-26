package com.nexign.dto.ng_psi;

import lombok.Value;

@Value
public class ProductInventoryEvent {
    String refId;
    String refIdType;
    String eventType;
    String sourceCode;
}
