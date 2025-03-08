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
@DiscriminatorValue("COURANT")
public class CompteCourant extends Compte {

    @Column(nullable = true)
    private BigDecimal montantDecouvert;

    @Override
    public boolean depot(BigDecimal montant) throws AccountException {
    if(montant ==  null || montant.compareTo(BigDecimal.ZERO)<= 0){
            throw new AccountException("Le montant doit être supérieur à 0.");
        }
        solde= solde.add(montant);
        return true;
    }

    @Override
    public boolean retrait(BigDecimal montant) throws AccountException {
        if (montant.compareTo(BigDecimal.ZERO) <= 0) {
            throw new AccountException("Le montant doit être supérieur à 0.");
        }
        if (solde.add(montantDecouvert).compareTo(montant) >= 0) {
            if(solde.compareTo(montant)>= 0){
                solde = solde.subtract(montant);
            }else{
                BigDecimal soldeRestant= solde;
                BigDecimal MontantRestantDebiter= montant.subtract(soldeRestant);
                montantDecouvert= montantDecouvert.subtract(MontantRestantDebiter);
                solde= BigDecimal.ZERO;
            }
            return true;
        }else {
            throw new AccountException("Votre solde est insuffisant.");
        }
    }
}
