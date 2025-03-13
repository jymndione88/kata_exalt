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
public class CompteCourant extends Compte {

    protected BigDecimal montantDecouvert;

    public CompteCourant(UUID id, String numeroCompte, BigDecimal solde, TypeCompte typeCompte, BigDecimal montantDecouvert) {
        super(id, numeroCompte, solde, typeCompte);
        this.montantDecouvert = montantDecouvert;
    }

    @Override
    public boolean depot(BigDecimal montant, MessageSource messageSource)  {
        if(montant ==  null || montant.compareTo(BigDecimal.ZERO)<= 0){
            throw new ExceptionFonctionnelle(messageSource.getMessage("compte.montant.valide", null,Locale.getDefault()));
        }
        solde= solde.add(montant);
        return true;
    }

    @Override
    public boolean retrait(BigDecimal montant, MessageSource messageSource)  {
        if (montant.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ExceptionFonctionnelle(messageSource.getMessage("compte.montant.valide", null,Locale.getDefault()));
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
            throw new ExceptionFonctionnelle(messageSource.getMessage("compte.solde.insuffisant", null,Locale.getDefault()));
        }
    }
}
