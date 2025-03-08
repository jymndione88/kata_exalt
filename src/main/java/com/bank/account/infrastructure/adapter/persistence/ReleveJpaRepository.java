package com.bank.account.infrastructure.adapter.persistence;

import com.bank.account.domain.model.ReleveBancaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReleveJpaRepository extends JpaRepository<ReleveBancaire, UUID> {

}
