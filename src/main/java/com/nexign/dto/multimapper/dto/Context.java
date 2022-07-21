package com.nexign.dto.multimapper.dto;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class Context {
    String space;
    String name;
    Object value;
}
