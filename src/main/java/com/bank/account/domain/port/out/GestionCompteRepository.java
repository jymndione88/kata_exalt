package com.bank.account.domain.port.out;

import com.bank.account.domain.model.Compte;
import com.bank.account.domain.model.Operation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GestionCompteRepository {

    Optional<Compte> findById(UUID id);
    void save(Compte compte);

}
