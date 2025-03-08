package com.bank.account.infrastructure.adapter.persistence;

import com.bank.account.domain.model.Operation;
import com.bank.account.domain.model.ReleveBancaire;
import com.bank.account.domain.port.out.GestionOperationRepository;
import com.bank.account.domain.port.out.GestionReleveRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ReleveRepository implements GestionReleveRepository {

    private final ReleveJpaRepository releveJpaRepository;

    public ReleveRepository(ReleveJpaRepository releveJpaRepository) {
        this.releveJpaRepository = releveJpaRepository;
    }

    @Override
    public void sauvegarderReleve(ReleveBancaire releve) {
        releveJpaRepository.save(releve);
    }
}
