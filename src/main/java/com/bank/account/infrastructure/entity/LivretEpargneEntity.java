package com.bank.account.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DiscriminatorValue("LIVRET")
@Entity
public class LivretEpargneEntity extends CompteEntity {

    @Column(nullable = true)
    protected  BigDecimal plafond;
}
