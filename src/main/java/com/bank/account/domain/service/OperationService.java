package com.bank.account.domain.service;

import com.bank.account.domain.model.Operation;
import com.bank.account.domain.port.in.InOperation;
import com.bank.account.domain.port.out.OutOperation;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OperationService implements InOperation {

    private final OutOperation outOperation;

    public OperationService(OutOperation outOperation) {
        this.outOperation = outOperation;
    }

    @Override
    public List<Operation> getOperationsByCompte(String numeroCompte)  {
        List<Operation> operations = outOperation.getOperationsByCompte(numeroCompte)
                .orElseThrow();

        return operations.stream()
                .sorted(Comparator.comparing(Operation::getDateOperation).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public void enregistrerOperation(Operation operation) {
        outOperation.enregistrerOperation(operation);
    }

}
