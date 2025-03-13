package com.bank.account.domain.port.in;

import com.bank.account.domain.model.Compte;
import com.bank.account.domain.model.Operation;
import com.bank.account.domain.model.TypeCompte;

public interface InCompte {

    boolean operation(Operation operation);
    Compte creerCompte(TypeCompte typeCompte);
}
