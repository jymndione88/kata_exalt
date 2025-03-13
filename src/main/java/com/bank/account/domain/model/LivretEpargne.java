package com.bank.account.domain.model;

import com.bank.account.application.exception.ExceptionFonctionnelle;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.context.MessageSource;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class LivretEpargne extends Compte {

    protected BigDecimal plafond;

    public LivretEpargne(UUID id, String numeroCompte, BigDecimal solde, TypeCompte typeCompte, BigDecimal plafond) {
        super(id, numeroCompte, solde, typeCompte);
        this.plafond = plafond;
    }

    @Override
    public boolean depot(BigDecimal montant, MessageSource messageSource) {
        if (montant == null || montant.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ExceptionFonctionnelle(messageSource.getMessage("compte.montant.valide", null, Locale.getDefault()));
        }
        if (solde.add(montant).compareTo(plafond) > 0) {
            throw new ExceptionFonctionnelle(messageSource.getMessage("compte.plafond.atteint", null, Locale.getDefault()));
        }
        solde = solde.add(montant);
        return true;
    }

    @Override
    public boolean retrait(BigDecimal montant, MessageSource messageSource)  {
        if (montant == null || montant.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ExceptionFonctionnelle(messageSource.getMessage("compte.montant.valide", null,Locale.getDefault()));
        }
        if (solde.compareTo(montant) < 0) {
            throw new ExceptionFonctionnelle(messageSource.getMessage("compte.solde.insuffisant", null,Locale.getDefault()));
        }
        solde = solde.subtract(montant);
        return true;
    }
}
