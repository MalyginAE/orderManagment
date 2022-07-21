package com.nexign.dto.order.context;

import lombok.Data;

@Data
public
class MultisubscriptionRelatedParties {
    String msisdn;
    Integer contactMsisdn;
    String cdiPartyId;
    String partyRoleId;
}
