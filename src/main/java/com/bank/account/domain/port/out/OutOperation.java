package com.bank.account.domain.port.out;

import com.bank.account.domain.model.Operation;

import java.util.List;
import java.util.Optional;

public interface OutOperation {

    Optional<List<Operation>> getOperationsByCompte(String numeroCompte);
    void enregistrerOperation(Operation operation);
}
