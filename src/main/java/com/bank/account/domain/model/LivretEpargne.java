package com.bank.account.domain.model;

import com.bank.account.domain.exception.AccountException;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@DiscriminatorValue("LIVRET_EPARGNE")
public class LivretEpargne extends Compte {

    @Column(nullable = true)
    public  BigDecimal plafond;

    @Override
    public boolean depot(BigDecimal montant) throws AccountException {
        if (montant == null || montant.compareTo(BigDecimal.ZERO) <= 0) {
            throw new AccountException("Le montant du dépôt doit être supérieur à 0.");
        }
        if (solde.add(montant).compareTo(plafond) > 0) {
            throw new AccountException("Votre plafond est atteint, vous ne pouvez pas déposer ce montant.");
        }
        solde = solde.add(montant);
        return true;
    }

    @Override
    public boolean retrait(BigDecimal montant) throws AccountException {
        if (montant == null || montant.compareTo(BigDecimal.ZERO) <= 0) {
            throw new AccountException("Le montant du retrait doit être supérieur à 0.");
        }
        if (solde.compareTo(montant) < 0) {
            throw new AccountException("Votre solde est insuffisant.");
        }
        solde = solde.subtract(montant);
        return true;
    }
}
