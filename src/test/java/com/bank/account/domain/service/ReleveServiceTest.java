package com.bank.account.domain.service;

import com.bank.account.domain.model.*;
import com.bank.account.domain.port.out.OutCompte;
import com.bank.account.domain.port.out.OutOperation;
import com.bank.account.domain.port.out.OutReleve;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

class ReleveServiceTest {

    @Mock
    private OutReleve outReleve;

    @Mock
    private OutCompte outCompte;

    @Mock
    private OutOperation outOperation;

    private ReleveService releveService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
       // operationService= new OperationService(outOperation);
        releveService= new ReleveService(outOperation, outCompte, outReleve);
    }

    @Test
    void getReleveBancaire() {
        // given
        Compte compte= CompteCourant.builder()
                .montantDecouvert(new BigDecimal("100.00"))
                .build();
        compte.setNumeroCompte("FR123456788");
        compte.setSolde(new BigDecimal("700.00"));

        Operation operation1= Operation.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440004"))
                .dateOperation(LocalDate.now())
                .montant(BigDecimal.valueOf(1234))
                .typeOperation(TypeOperation.RETRAIT)
                .build();
        Operation operation2= Operation.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440005"))
                .dateOperation(LocalDate.now())
                .montant(BigDecimal.valueOf(1234))
                .typeOperation(TypeOperation.RETRAIT)
                .build();

        List<Operation> operations= List.of(operation1, operation2);

        Mockito.when(outCompte.findByNumeroCompte(compte.getNumeroCompte())).thenReturn(Optional.of(compte));
        Mockito.when(outOperation.getOperationsByCompte(compte.getNumeroCompte())).thenReturn(Optional.of(operations));

        // when
        ReleveBancaire releve= releveService.getReleveBancaire(compte.getNumeroCompte());

        // then
        Assertions.assertNotNull(releve);
        Assertions.assertEquals(2, releve.getOperations().size());
        Assertions.assertEquals("FR123456788", releve.getNumeroCompte());
    }

}