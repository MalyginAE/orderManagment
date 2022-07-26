package com.nexign.dto.partner_service;

import lombok.Value;

@Value
public class Parameters {
    String productOfferingId;
    ClientInfo clientInfo;
    Object channel;
    ExtraParams extra_params;
}
