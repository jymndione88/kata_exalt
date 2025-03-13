package com.bank.account.domain.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.context.MessageSource;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class Compte {

    protected UUID id;

    protected String numeroCompte;

    protected BigDecimal solde;

    protected TypeCompte typeCompte;

    public abstract boolean depot(BigDecimal montant, MessageSource messageSource) ;

    public abstract boolean retrait(BigDecimal montant, MessageSource messageSource);
}
