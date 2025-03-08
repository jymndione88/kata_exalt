package com.bank.account.infrastructure.adapter.persistence;

import com.bank.account.domain.model.Operation;
import com.bank.account.domain.port.out.GestionCompteRepository;
import com.bank.account.domain.model.Compte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CompteRepository implements GestionCompteRepository {

    private final CompteJpaRepository compteJpaRepository;

    public CompteRepository(CompteJpaRepository compteJpaRepository) {
        this.compteJpaRepository = compteJpaRepository;
    }

    @Override
    public Optional<Compte> findById(UUID id) {
        return compteJpaRepository.findById(id);
    }

    @Override
    public void save(Compte compte) {
        compteJpaRepository.save(compte);
    }

}
