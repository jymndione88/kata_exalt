package com.bank.account.domain.port.in;

import com.bank.account.domain.exception.AccountException;
import com.bank.account.domain.model.Operation;
import com.bank.account.domain.model.ReleveBancaire;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface GestionOperation {

    List<Operation> getOperationsByCompte(String numeroCompte) throws AccountException;
    void enregistrerOperation(Operation operation) throws AccountException;
}
