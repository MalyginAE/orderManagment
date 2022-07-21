package com.nexign.dto.multimapper.dto;

import com.nexign.dto.order.context.RelatedItem;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class MultimapperResponseBodyDto {
    ConversionResult conversionResult;
    TotalResult totalResult;
    String timestamp;
}


