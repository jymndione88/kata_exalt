package com.bank.account.domain.port.in;

import com.bank.account.domain.model.Operation;
import com.bank.account.domain.model.ReleveBancaire;

import java.util.List;
import java.util.UUID;

public interface GestionReleve {

    ReleveBancaire getReleveBancaire(UUID id) throws Exception;
}
