package com.bank.account.infrastructure.repository;

import com.bank.account.infrastructure.entity.OperationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OperationRepository extends JpaRepository<OperationEntity, UUID> {

    @Query("SELECT o FROM OperationEntity o WHERE o.numeroCompte = :numeroCompte")
    Optional<List<OperationEntity>> findByNumeroCompte(@Param("numeroCompte") String numeroCompte);
}
