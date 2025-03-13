package com.bank.account.domain.service;

import com.bank.account.application.exception.ExceptionFonctionnelle;
import com.bank.account.domain.model.*;
import com.bank.account.domain.port.in.InCompte;
import com.bank.account.domain.port.out.OutCompte;
import com.bank.account.domain.port.out.OutOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Locale;
import java.util.UUID;

public class CompteService implements InCompte {

    @Autowired
    MessageSource messageSource;

    private final OutCompte outCompte;
    private final OutOperation outOperation;
    @Value("${compte.livret.montant.plafond.initial}")
    private String PLAFOND;
    @Value("${compte.courant.montant.decouvert.initial}")
    private String DECOUVERT;

    public CompteService(OutCompte outCompte, OutOperation outOperation) {
        this.outCompte = outCompte;
        this.outOperation = outOperation;
    }

    @Override
    public boolean operation(Operation operation) {
        boolean success;
        Compte compte = outCompte.findByNumeroCompte(operation.getNumeroCompte())
                .orElseThrow(() -> new ExceptionFonctionnelle(messageSource.getMessage("compte.non.trouve", new Object[]{operation.getNumeroCompte()}, Locale.getDefault())));

        if (TypeOperation.DEPOT.name().equalsIgnoreCase(operation.getTypeOperation().name())) {
            success = compte.depot(operation.getMontant(), messageSource);
        } else if (TypeOperation.RETRAIT.name().equalsIgnoreCase(operation.getTypeOperation().name())) {
            success = compte.retrait(operation.getMontant(), messageSource);
        }
        else {
            throw new ExceptionFonctionnelle(messageSource.getMessage("compte.operation.invalide", null, Locale.getDefault()));
        }
        if (success) {
            outCompte.patchCompte(compte);

            operation.setDateOperation(LocalDate.now());
            operation.setMotif("A_RENSEIGNER");
            outOperation.enregistrerOperation(operation);
        }
        return success;
    }

    @Override
    public Compte creerCompte(TypeCompte typeCompte) {
      Compte compte= genererCompte(typeCompte);
        return outCompte.creerCompte(compte)
                .orElseThrow(() -> new ExceptionFonctionnelle(messageSource.getMessage("compte.creation.erreur", null, Locale.getDefault())));
    }

    public Compte genererCompte(TypeCompte typeCompte){
        UUID id= UUID.randomUUID();
        StringBuilder numeroCompte= new StringBuilder("FR");
        numeroCompte.append(id);

        if (TypeCompte.COURANT.toString().equalsIgnoreCase(typeCompte.toString())){
            return CompteCourant.builder()
                    .id(id)
                    .numeroCompte(numeroCompte.toString())
                    .solde(BigDecimal.ZERO)
                    .typeCompte(TypeCompte.COURANT)
                    .montantDecouvert(new BigDecimal(DECOUVERT))
                    .build();
        }else{
            return LivretEpargne.builder()
                    .id(id)
                    .numeroCompte(numeroCompte.toString())
                    .solde(BigDecimal.ZERO)
                    .typeCompte(TypeCompte.LIVRET)
                    .plafond(new BigDecimal(PLAFOND))
                    .build();
        }

    }
}
