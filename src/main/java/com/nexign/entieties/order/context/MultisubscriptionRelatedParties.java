package com.nexign.entieties.order.context;

import lombok.Data;

@Data
public
class MultisubscriptionRelatedParties {
    String msisdn;
    String contactMsisdn;
    String cdiPartyId;
    String partyRoleId;
}
