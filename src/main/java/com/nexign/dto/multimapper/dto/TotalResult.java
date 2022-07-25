package com.nexign.dto.multimapper.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class TotalResult {
    String description;
    String code;
}
