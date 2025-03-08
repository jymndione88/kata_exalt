package com.bank.account.domain.model;

import com.bank.account.domain.exception.AccountException;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE", discriminatorType = DiscriminatorType.STRING)
@Table(name = "compte")
public abstract class Compte {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID id;

    @Column(nullable = false, unique = true)
    protected String numeroCompte;

    protected BigDecimal solde;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    protected TypeCompte typeCompte;

    public abstract boolean depot(BigDecimal montant) throws AccountException;

    public abstract boolean retrait(BigDecimal montant) throws AccountException;
}
