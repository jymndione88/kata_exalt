package com.bank.account.domain.port.in;

import com.bank.account.domain.model.Operation;
import com.bank.account.domain.model.ReleveBancaire;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface GestionCompte {

    boolean deposer(UUID id, BigDecimal montant) throws Exception;
    boolean retirer(UUID id, BigDecimal montant) throws Exception;
}
