package com.bank.account.domain.service;

import com.bank.account.domain.exception.AccountException;
import com.bank.account.domain.model.Compte;
import com.bank.account.domain.model.CompteCourant;
import com.bank.account.infrastructure.adapter.persistence.CompteRepository;
import com.bank.account.infrastructure.adapter.persistence.OperationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CompteServiceTest {

    @Mock
    private CompteRepository compteRepository;

    @Mock
    private OperationRepository operationRepository;

    private CompteService compteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        compteService= new CompteService(compteRepository, operationRepository);
    }

    @Test
    void testDeposer() throws AccountException {
        // given
        UUID id = UUID.randomUUID();
        BigDecimal montant = new BigDecimal("95.00");
        Compte compte= CompteCourant.builder()
                .montantDecouvert(new BigDecimal(100.00))
                .build();
        compte.setNumeroCompte("550e8400-e29b-41d4-a716-446655440000");
        compte.setSolde(new BigDecimal(700.00));

        Mockito.when(compteRepository.findById(id)).thenReturn(Optional.of(compte));

        // when
        boolean retirer= compteService.deposer(id, montant);

        // then
        Assertions.assertTrue(retirer);
        Assertions.assertEquals(new BigDecimal("795.00"), compte.getSolde());
    }

    @Test
    void testRetirer() throws AccountException {
        // given
        UUID id = UUID.randomUUID();
        BigDecimal montant = new BigDecimal("95.00");
        Compte compte= CompteCourant.builder()
                .montantDecouvert(new BigDecimal(100.00))
                .build();
        compte.setNumeroCompte("550e8400-e29b-41d4-a716-446655440000");
        compte.setSolde(new BigDecimal(700.00));

        Mockito.when(compteRepository.findById(id)).thenReturn(Optional.of(compte));

        // when
       boolean retirer= compteService.retirer(id, montant);

        // then
        Assertions.assertTrue(retirer);
        Assertions.assertEquals(new BigDecimal("605.00"), compte.getSolde());
    }


}