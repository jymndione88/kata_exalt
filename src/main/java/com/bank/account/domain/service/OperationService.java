package com.bank.account.domain.service;

import com.bank.account.domain.exception.AccountException;
import com.bank.account.domain.model.Operation;
import com.bank.account.domain.port.in.GestionOperation;
import com.bank.account.infrastructure.adapter.persistence.OperationRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OperationService implements GestionOperation {

    private final OperationRepository operationRepository;

    public OperationService(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    @Override
    public List<Operation> getOperationsByCompte(String numeroCompte) throws AccountException {
        List<Operation> operations = operationRepository.getOperationsByCompte(numeroCompte)
                .orElseThrow(() -> new AccountException("Opérations non trouvées."));

        return operations.stream()
                .sorted(Comparator.comparing(Operation::getDateEmission).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public void enregistrerOperation(Operation operation) throws AccountException {
        operationRepository.enregistrerOperation(operation);
    }

}
