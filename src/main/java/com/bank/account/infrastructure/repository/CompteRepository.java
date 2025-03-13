package com.bank.account.infrastructure.repository;

import com.bank.account.infrastructure.entity.CompteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompteRepository extends JpaRepository<CompteEntity, UUID> {

    @Query("SELECT c FROM CompteEntity c WHERE c.numeroCompte = :numeroCompte")
    Optional<CompteEntity> findByNumeroCompte(@Param("numeroCompte") String numeroCompte);
}
