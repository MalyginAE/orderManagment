package com.nexign.dto.multimapper.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@RequiredArgsConstructor
public class MultimapperConversionRequestBody {
    String conversionType;
    List<Context> context;
    List<Item> items;
}




