package com.bank.account.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class ReleveBancaire {

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
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    protected List<Operation> operations;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    protected TypeCompte typeCompte;

}
