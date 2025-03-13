package com.bank.account.domain.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class Operation {

    protected UUID id;

    protected String numeroCompte;

    protected LocalDate dateOperation;

    protected BigDecimal montant;

    protected String motif;

    protected TypeOperation typeOperation;
}
