package com.nexign.dto.bss;

import lombok.Value;

import java.util.ArrayList;
@Value
public class CcmProductOfferingCheackOrderResponceBodyDto {
    ArrayList<BssCheckItems> items;
    BssListInfo listInfo;
}




