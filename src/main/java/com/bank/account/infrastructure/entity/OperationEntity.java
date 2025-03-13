package com.bank.account.infrastructure.entity;

import com.bank.account.domain.model.TypeOperation;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "operation")
public class OperationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID id;

    @Column(nullable = false)
    protected String numeroCompte;

    @Column(nullable = false)
    protected LocalDate dateOperation;

    @Column(nullable = false)
    protected BigDecimal montant;

    @Column(nullable = true)
    protected String motif;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    protected TypeOperation typeOperation;

}
