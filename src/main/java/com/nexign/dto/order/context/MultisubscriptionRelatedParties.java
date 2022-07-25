package com.nexign.dto.order.context;

import lombok.Data;
import lombok.Value;

@Value
public class MultisubscriptionRelatedParties {
    String msisdn;
    Integer contactMsisdn;
    String cdiPartyId;
    String partyRoleId;
}
