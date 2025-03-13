package com.bank.account.application.dto.out;

import com.bank.account.domain.model.TypeCompte;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ResponseCompteDto {

    private UUID id;

    protected String numeroCompte;

    protected BigDecimal solde;

    protected TypeCompte typeCompte;
}
