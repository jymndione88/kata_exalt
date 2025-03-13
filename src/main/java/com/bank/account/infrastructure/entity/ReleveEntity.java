package com.bank.account.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "releve")
public class ReleveEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID id;

    @Column(nullable = false)
    protected String numeroCompte;

    @Column(nullable = false)
    protected LocalDate date;

    @Column(nullable = false)
    protected BigDecimal soldeActuel;

    @Column(nullable = false)
    @OneToMany(cascade = CascadeType.MERGE, orphanRemoval = true)
    protected List<OperationEntity> operations;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    protected TypeCompte typeCompte;

}
