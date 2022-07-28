package com.nexign.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

@Data
@AllArgsConstructor
public class FabricActionMap {
    String fabricProductOfferingId;
    String correlationId;
    String status;
}