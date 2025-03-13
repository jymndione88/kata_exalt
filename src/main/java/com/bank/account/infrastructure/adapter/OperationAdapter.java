package com.bank.account.infrastructure.adapter;

import com.bank.account.application.exception.ExceptionFonctionnelle;
import com.bank.account.domain.model.Operation;
import com.bank.account.domain.port.out.OutOperation;
import com.bank.account.infrastructure.mapper.OperationMapper;
import com.bank.account.infrastructure.repository.OperationRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OperationAdapter implements OutOperation {

    private final OperationRepository operationRepository;
    private final OperationMapper operationMapper;

    public OperationAdapter(OperationRepository operationRepository, OperationMapper operationMapper) {
        this.operationRepository = operationRepository;
        this.operationMapper= operationMapper;
    }

    @Override
    public Optional<List<Operation>> getOperationsByCompte(String numeroCompte) {
        return operationRepository.findByNumeroCompte(numeroCompte)
                .map(operations -> operations.stream()
                        .map(operationMapper::mapToDomaine)
                        .collect(Collectors.toList()));
    }

    @Transactional
    @Override
    public void enregistrerOperation(Operation operation) {
        try {
            var operationEntity= operationMapper.mapToEntity(operation);
            operationRepository.save(operationEntity);
            log.info("Opération enregistré: {}.", operationEntity);
        }catch (Exception ex){
            log.error("Erreur lors de l'enregistrement de l'opération." + ex.getMessage());
            throw new ExceptionFonctionnelle("Erreur lors de l'enregistrement de l'opération.");
        }
    }
}
