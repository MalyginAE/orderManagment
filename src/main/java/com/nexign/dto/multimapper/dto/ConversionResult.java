package com.nexign.dto.multimapper.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ConversionResult {
    List<ConvertedItem> convertedItems;
    List<Context> context;
    String conversionType;
}
