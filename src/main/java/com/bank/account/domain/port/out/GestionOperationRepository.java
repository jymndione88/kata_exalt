package com.bank.account.domain.port.out;

import com.bank.account.domain.model.Compte;
import com.bank.account.domain.model.Operation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GestionOperationRepository {

    Optional<List<Operation>> getOperationsByCompte(String numeroCompte);
    void enregistrerOperation(Operation operation);
}
