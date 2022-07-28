package com.nexign.dto.bss;

import lombok.Value;

import java.util.List;
@Value
public class CcmProductOfferingCheackOrderRequestBodyDto {
    List<String> productOfferingIds;
    List<Integer> productStatusIds;

}
