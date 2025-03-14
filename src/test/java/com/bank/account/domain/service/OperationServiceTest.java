package com.bank.account.domain.service;

import com.bank.account.domain.model.Compte;
import com.bank.account.domain.model.CompteCourant;
import com.bank.account.domain.model.Operation;
import com.bank.account.domain.model.TypeOperation;
import com.bank.account.domain.port.out.OutOperation;
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

class OperationServiceTest {

    @Mock
    private OutOperation outOperation;

    private OperationService operationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        operationService= new OperationService(outOperation);
    }

    @Test
    void getOperationsByCompte() {
        // Given
        Compte compte= CompteCourant.builder()
                .montantDecouvert(new BigDecimal("100.00"))
                .build();
        compte.setNumeroCompte("550e8400-e29b-41d4-a716-446655440000");
        compte.setSolde(new BigDecimal(700.00));

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

        List<Operation> ListeOperations= List.of(operation1, operation2);

        Mockito.when(outOperation.getOperationsByCompte(compte.getNumeroCompte())).thenReturn(Optional.of(ListeOperations));

        // When
        List<Operation> operations= operationService.getOperationsByCompte(compte.getNumeroCompte());

        // Then
        Assertions.assertEquals(2, operations.size());
        Assertions.assertEquals("550e8400-e29b-41d4-a716-446655440004", operations.get(0).getId().toString());
    }

    @Test
    void enregistrerOperation() {
        // Given
        Operation operation= Operation.builder()
                .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440004"))
                .dateOperation(LocalDate.now())
                .montant(BigDecimal.valueOf(1234))
                .typeOperation(TypeOperation.RETRAIT)
                .build();

        Mockito.doNothing().when(outOperation).enregistrerOperation(Mockito.any(Operation.class));

        // When
        operationService.enregistrerOperation(operation);

        // Then
        Mockito.verify(outOperation, Mockito.times(1)).enregistrerOperation(Mockito.any(Operation.class));
    }
}