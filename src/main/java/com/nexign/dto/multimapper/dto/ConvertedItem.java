package com.nexign.dto.multimapper.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ConvertedItem {
    ItemsResult itemsResult;
    List<OutputItem> outputItems;
    List<InputItem> inputItems;
}
