package com.bank.account.domain.port.out;

import com.bank.account.domain.model.Compte;

import java.util.Optional;

public interface OutCompte {

    Optional<Compte> findByNumeroCompte(String id);
    void patchCompte(Compte compte);
    Optional<Compte> creerCompte(Compte compte);
}
