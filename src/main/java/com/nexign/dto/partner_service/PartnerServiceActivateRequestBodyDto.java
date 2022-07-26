package com.nexign.dto.partner_service;

import lombok.Value;
@Value
public class PartnerServiceActivateRequestBodyDto {
    String jsonrpc;
    String id;
    String method;
    Parameters params;

}

