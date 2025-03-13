package com.bank.account.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "compte")
public class CompteEntity {

    @Id
    protected UUID id;

    @Column(nullable = false, unique = true)
    protected String numeroCompte;

    protected BigDecimal solde;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    protected TypeCompte typeCompte;

    protected BigDecimal montantDecouvert;

    protected  BigDecimal plafond;
}

