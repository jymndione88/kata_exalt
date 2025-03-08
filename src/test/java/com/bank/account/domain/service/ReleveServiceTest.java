package com.bank.account.domain.service;

import com.bank.account.domain.exception.AccountException;
import com.bank.account.domain.model.*;
import com.bank.account.infrastructure.adapter.persistence.CompteRepository;
import com.bank.account.infrastructure.adapter.persistence.OperationRepository;
import com.bank.account.infrastructure.adapter.persistence.ReleveRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ReleveServiceTest {

    @Mock
    private ReleveRepository releveRepository;

    @Mock
    private CompteRepository compteRepository;

    @Mock
    private OperationRepository operationRepository;

    private ReleveService releveService;

    private OperationService operationService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        operationService= new OperationService(operationRepository);
        releveService= new ReleveService(operationService, compteRepository, releveRepository);
    }

    @Test
    void getReleveBancaire() throws AccountException {
        // given
        UUID id = UUID.randomUUID();
        Compte compte= CompteCourant.builder()
                .montantDecouvert(new BigDecimal(100.00))
                .build();
        compte.setNumeroCompte("550e8400-e29b-41d4-a716-446655440000");
        compte.setSolde(new BigDecimal(700.00));

        Operation operation1= Operation.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440004"))
                .dateEmission(LocalDate.now())
                .montant(BigDecimal.valueOf(1234))
                .typeOperation(TypeOperation.RETRAIT)
                .build();
        Operation operation2= Operation.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440005"))
                .dateEmission(LocalDate.now())
                .montant(BigDecimal.valueOf(1234))
                .typeOperation(TypeOperation.RETRAIT)
                .build();

        List<Operation> operations= List.of(operation1, operation2);

        Mockito.when(compteRepository.findById(id)).thenReturn(Optional.of(compte));
        Mockito.when(operationRepository.getOperationsByCompte(compte.getNumeroCompte())).thenReturn(Optional.of(operations));

        // when
        ReleveBancaire releve= releveService.getReleveBancaire(id);

        // then
        Assertions.assertNotNull(releve);
        Assertions.assertEquals(2, releve.getOperations().size());
        Assertions.assertEquals("550e8400-e29b-41d4-a716-446655440000", releve.getNumeroCompte());
    }

}