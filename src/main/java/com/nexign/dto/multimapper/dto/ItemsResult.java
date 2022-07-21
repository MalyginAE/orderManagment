package com.nexign.dto.multimapper.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ItemsResult {
    List<UnresolvedCondition> unresolvedConditions;
    String description;
    String code;
}
