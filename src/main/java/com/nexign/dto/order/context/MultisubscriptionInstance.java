package com.nexign.dto.order.context;

import lombok.Data;
import lombok.Value;

import java.util.List;

@Value
public class MultisubscriptionInstance {
    String instanceId;
    String type;
    String name;
    List<MultisubscriptionCharacteristic> characteristics;
    String state;
    String externalState;
    String startDate;
    String terminationDate;
    Object entity;
}
