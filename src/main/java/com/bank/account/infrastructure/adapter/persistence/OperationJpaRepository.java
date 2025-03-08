package com.bank.account.infrastructure.adapter.persistence;

import com.bank.account.domain.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OperationJpaRepository extends JpaRepository<Operation, UUID> {

    @Query("SELECT o FROM Operation o WHERE o.numeroCompte = :numeroCompte")
    Optional<List<Operation>> findByNumeroCompte(@Param("numeroCompte") String numeroCompte);
}
