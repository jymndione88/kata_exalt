package com.bank.account.domain.port.in;

import com.bank.account.domain.model.ReleveBancaire;

public interface InReleve {

    ReleveBancaire getReleveBancaire(String numeroCompte);
}
