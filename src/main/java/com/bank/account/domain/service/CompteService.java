package com.bank.account.domain.service;

import com.bank.account.domain.exception.AccountException;
import com.bank.account.domain.model.Operation;
import com.bank.account.domain.model.TypeOperation;
import com.bank.account.domain.port.in.GestionCompte;
import com.bank.account.domain.model.Compte;
import com.bank.account.infrastructure.adapter.persistence.CompteRepository;
import com.bank.account.infrastructure.adapter.persistence.OperationRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class CompteService implements GestionCompte {

    private final CompteRepository compteRepository;
    private final OperationRepository operationRepository;

    public CompteService(CompteRepository compteRepository, OperationRepository operationRepository) {
        this.compteRepository = compteRepository;
        this.operationRepository = operationRepository;
    }

    @Transactional
    @Override
    public boolean deposer(UUID id, BigDecimal montant) throws AccountException {
        Compte compte = compteRepository.findById(id)
                .orElseThrow(() -> new AccountException("Ce compte n'existe pas."));

        boolean success = compte.depot(montant);
        if (success) {
            compteRepository.save(compte);

            //on enregistre l'operation
            Operation operation= Operation.builder()
                    .numeroCompte(compte.getNumeroCompte())
                    .dateEmission(LocalDate.now())
                    .montant(montant)
                    .motif("A_RENSEIGNER")
                    .typeOperation(TypeOperation.DEPOT)
                    .build();
            operationRepository.enregistrerOperation(operation);
        }
        return success;
    }

    @Transactional
    @Override
    public boolean retirer(UUID id, BigDecimal montant) throws AccountException {

        Compte compte = compteRepository.findById(id)
                .orElseThrow(() -> new AccountException("Ce compte n'existe pas."));

        boolean success = compte.retrait(montant);
        if (success) {
            compteRepository.save(compte);

            //on enregistre l'operation
            Operation operation= Operation.builder()
                    .numeroCompte(compte.getNumeroCompte())
                    .dateEmission(LocalDate.now())
                    .montant(montant)
                    .motif("A_RENSEIGNER")
                    .typeOperation(TypeOperation.RETRAIT)
                    .build();
            operationRepository.enregistrerOperation(operation);
        }
        return success;
    }
}
