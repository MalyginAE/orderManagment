package com.nexign.dto.order.context;

import lombok.Data;
import lombok.Value;

@Value
public class MultisubscriptionOrderCharacteristic {
    String space;
    String name;
    Object value;
}
