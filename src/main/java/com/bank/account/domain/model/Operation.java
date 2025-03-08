package com.bank.account.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID id;

    @Column(nullable = false)
    protected String numeroCompte;

    @Column(nullable = false)
    protected LocalDate dateEmission;

    @Column(nullable = false)
    protected BigDecimal montant;

    @Column(nullable = true)
    protected String motif;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    protected TypeOperation typeOperation;

}
