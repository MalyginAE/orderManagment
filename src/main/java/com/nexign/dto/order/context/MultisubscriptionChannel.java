package com.nexign.dto.order.context;

import lombok.Value;

@Value
public class MultisubscriptionChannel {
    String channelId;
    String name;
    String role;
    String type;
}
