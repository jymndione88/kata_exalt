package com.bank.account.domain.service;

import com.bank.account.domain.exception.AccountException;
import com.bank.account.domain.model.Compte;
import com.bank.account.domain.model.Operation;
import com.bank.account.domain.model.ReleveBancaire;
import com.bank.account.domain.port.in.GestionOperation;
import com.bank.account.domain.port.in.GestionReleve;
import com.bank.account.infrastructure.adapter.persistence.CompteRepository;
import com.bank.account.infrastructure.adapter.persistence.OperationRepository;
import com.bank.account.infrastructure.adapter.persistence.ReleveRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReleveService implements GestionReleve {

    private final OperationService operationService;
    private final CompteRepository compteRepository;
    private final ReleveRepository releveRepository;

    public ReleveService(OperationService operationService, CompteRepository compteRepository, ReleveRepository releveRepository) {
        this.operationService = operationService;
        this.compteRepository = compteRepository;
        this.releveRepository = releveRepository;
    }

    @Transactional
    @Override
    public ReleveBancaire getReleveBancaire(UUID id) throws AccountException {
        Compte compte = compteRepository.findById(id)
                .orElseThrow(() -> new AccountException("Ce compte n'existe pas."));

        List<Operation> operations= operationService.getOperationsByCompte(compte.getNumeroCompte());

        ReleveBancaire releve= ReleveBancaire.builder()
                .numeroCompte(compte.getNumeroCompte())
                .date(LocalDate.now())
                .soldeActuel(compte.getSolde())
                .operations(operations)
                .typeCompte(compte.getTypeCompte())
                .build();

        releveRepository.sauvegarderReleve(releve);
        return releve;

    }
}
