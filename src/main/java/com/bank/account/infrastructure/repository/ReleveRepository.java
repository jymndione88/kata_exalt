package com.bank.account.infrastructure.repository;

import com.bank.account.infrastructure.entity.ReleveEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReleveRepository extends JpaRepository<ReleveEntity, UUID> {

}
