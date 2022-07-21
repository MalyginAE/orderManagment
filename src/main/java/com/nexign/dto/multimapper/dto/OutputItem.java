package com.nexign.dto.multimapper.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OutputItem {
    String itemType;
    String itemProvider;
    String name;
    String refId;
}
