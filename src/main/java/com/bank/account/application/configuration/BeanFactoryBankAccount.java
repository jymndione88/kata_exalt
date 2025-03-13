package com.bank.account.application.configuration;

import com.bank.account.domain.port.in.InCompte;
import com.bank.account.domain.port.in.InOperation;
import com.bank.account.domain.port.in.InReleve;
import com.bank.account.domain.port.out.OutCompte;
import com.bank.account.domain.port.out.OutOperation;
import com.bank.account.domain.port.out.OutReleve;
import com.bank.account.domain.service.CompteService;
import com.bank.account.domain.service.OperationService;
import com.bank.account.domain.service.ReleveService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanFactoryBankAccount {
    @Bean(name = "CompteService")
    public InCompte CompteService(OutCompte outCompte, OutOperation outOperation){
        return new CompteService(outCompte, outOperation);
    }

    @Bean(name = "OperationService")
    public InOperation OperationService(OutOperation outOperation){
        return new OperationService(outOperation);
    }

    @Bean(name = "ReleveService")
    public InReleve ReleveService(OutOperation outOperation, OutCompte outCompte, OutReleve outReleve){
        return new ReleveService(outOperation, outCompte, outReleve);
    }
}
