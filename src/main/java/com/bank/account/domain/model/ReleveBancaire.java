package com.bank.account.domain.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class ReleveBancaire {

    protected UUID id;

    protected String numeroCompte;

    protected LocalDate date;

    protected BigDecimal soldeActuel;

    protected List<Operation> operations;

    protected TypeCompte typeCompte;
}
