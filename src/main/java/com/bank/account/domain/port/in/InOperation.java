package com.bank.account.domain.port.in;

import com.bank.account.domain.model.Operation;

import java.util.List;


public interface InOperation {

    List<Operation> getOperationsByCompte(String numeroCompte);
    void enregistrerOperation(Operation operation);
}
