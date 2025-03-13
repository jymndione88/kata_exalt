package com.bank.account.application.dto.in;

import com.bank.account.domain.model.TypeOperation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OperationDto {

    @NotNull(message = "le type d'opération est obligatoire")
    @Schema(description = "Le type d'opération")
    protected TypeOperation typeOperation;

    @NotNull(message = "le montant est obligatoire")
    @Schema(description = "Le montant de l'opération")
    protected BigDecimal montant;

}
