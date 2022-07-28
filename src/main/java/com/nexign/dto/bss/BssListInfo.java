package com.nexign.dto.bss;

import lombok.Value;

@Value
public class BssListInfo {
    String limit;
    String offset;
    Integer count;
}
