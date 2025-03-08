package com.bank.account.infrastructure.adapter.persistence;

import com.bank.account.domain.model.Compte;
import com.bank.account.domain.model.Operation;
import com.bank.account.domain.port.out.GestionCompteRepository;
import com.bank.account.domain.port.out.GestionOperationRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class OperationRepository implements GestionOperationRepository {

    private final OperationJpaRepository operationJpaRepository;

    public OperationRepository(OperationJpaRepository operationJpaRepository) {
        this.operationJpaRepository = operationJpaRepository;
    }

    @Override
    public Optional<List<Operation>> getOperationsByCompte(String numeroCompte) {
        return operationJpaRepository.findByNumeroCompte(numeroCompte);
    }

    @Override
    public void enregistrerOperation(Operation operation) {
        operationJpaRepository.save(operation);
    }
}
