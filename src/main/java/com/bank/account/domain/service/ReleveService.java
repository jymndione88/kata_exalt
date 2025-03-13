package com.bank.account.domain.service;

import com.bank.account.application.exception.ExceptionFonctionnelle;
import com.bank.account.domain.model.Compte;
import com.bank.account.domain.model.Operation;
import com.bank.account.domain.model.ReleveBancaire;
import com.bank.account.domain.port.in.InReleve;
import com.bank.account.domain.port.out.OutCompte;
import com.bank.account.domain.port.out.OutOperation;
import com.bank.account.domain.port.out.OutReleve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class ReleveService implements InReleve {

    @Autowired
    MessageSource messageSource;

    private final OutOperation outOperation;
    private final OutCompte outCompte;
    private final OutReleve outReleve;

    public ReleveService(OutOperation outOperation, OutCompte outCompte, OutReleve outReleve) {
        this.outOperation = outOperation;
        this.outCompte = outCompte;
        this.outReleve = outReleve;
    }

    @Override
    public ReleveBancaire getReleveBancaire(String numeroCompte) {
        Compte compte = outCompte.findByNumeroCompte(numeroCompte)
                .orElseThrow(() -> new ExceptionFonctionnelle(messageSource.getMessage("compte.non.trouve", new Object[]{numeroCompte}, Locale.getDefault())));

        List<Operation> operations= List.of();
        Optional<List<Operation>> Optionaloperations= outOperation.getOperationsByCompte(compte.getNumeroCompte());
        if(Optionaloperations.isPresent()){
            operations= Optionaloperations.get();
        }

        ReleveBancaire releve= ReleveBancaire.builder()
                .numeroCompte(compte.getNumeroCompte())
                .date(LocalDate.now())
                .soldeActuel(compte.getSolde())
                .operations(operations)
                .typeCompte(compte.getTypeCompte())
                .build();

        outReleve.sauvegarderReleve(releve);
        return releve;

    }
}
