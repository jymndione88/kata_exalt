package com.bank.account.application.dto.out;

import com.bank.account.domain.model.Operation;
import com.bank.account.domain.model.TypeCompte;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class ResponseReleveDto {

    protected String numeroCompte;

    private LocalDate date;

    protected BigDecimal soldeActuel;

    protected List<Operation> operations;

    protected TypeCompte typeCompte;

}
