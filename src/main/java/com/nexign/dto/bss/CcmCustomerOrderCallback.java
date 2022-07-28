package com.nexign.dto.bss;

import lombok.Value;

import java.util.List;
@Value
public class CcmCustomerOrderCallback {
    Object orders;
    String type;
    List<CcmOrderEntity> orderEntities;
}
