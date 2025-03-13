package com.bank.account.domain.service;

import com.bank.account.domain.model.Compte;
import com.bank.account.domain.model.CompteCourant;
import com.bank.account.domain.model.Operation;
import com.bank.account.domain.model.TypeOperation;
import com.bank.account.domain.port.out.OutCompte;
import com.bank.account.domain.port.out.OutOperation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

class CompteServiceTest {

    @Mock
    private OutCompte outCompte;

    @Mock
    private OutOperation outOperation;

    private CompteService compteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        compteService= new CompteService(outCompte, outOperation);
    }

    @Test
    void testOperationDepot() {
        // given
        Compte compte= CompteCourant.builder()
                .montantDecouvert(new BigDecimal("100.00"))
                .build();
        compte.setNumeroCompte("FR123456788");
        compte.setSolde(new BigDecimal("700.00"));

        Operation operation= Operation.builder()
                .numeroCompte(compte.getNumeroCompte())
                .typeOperation(TypeOperation.DEPOT)
                .montant(new BigDecimal("95.00"))
                .build();

        Mockito.when(outCompte.findByNumeroCompte(compte.getNumeroCompte())).thenReturn(Optional.of(compte));

        // when
        boolean retirer= compteService.operation(operation);

        // then
        Assertions.assertTrue(retirer);
        Assertions.assertEquals(new BigDecimal("795.00"), compte.getSolde());
    }

    @Test
    void testOperationRetrait() {
        // given
        Compte compte= CompteCourant.builder()
                .montantDecouvert(new BigDecimal("100.00"))
                .build();
        compte.setNumeroCompte("FR123456788");
        compte.setSolde(new BigDecimal("700.00"));

        Operation operation= Operation.builder()
                .numeroCompte(compte.getNumeroCompte())
                .typeOperation(TypeOperation.RETRAIT)
                .montant(new BigDecimal("95.00"))
                .build();

        Mockito.when(outCompte.findByNumeroCompte(compte.getNumeroCompte())).thenReturn(Optional.of(compte));

        // when
        boolean retirer= compteService.operation(operation);

        // then
        Assertions.assertTrue(retirer);
        Assertions.assertEquals(new BigDecimal("605.00"), compte.getSolde());
    }

}