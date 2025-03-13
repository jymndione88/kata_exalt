package com.bank.account.domain.port.out;

import com.bank.account.domain.model.ReleveBancaire;

public interface OutReleve {

    void sauvegarderReleve(ReleveBancaire releve);
}
