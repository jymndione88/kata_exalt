package com.bank.account.domain.port.out;

import com.bank.account.domain.model.Operation;
import com.bank.account.domain.model.ReleveBancaire;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GestionReleveRepository {

    void sauvegarderReleve(ReleveBancaire releve);
}
