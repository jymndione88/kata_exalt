package com.bank.account.infrastructure.mapper;

import com.bank.account.domain.model.ReleveBancaire;
import com.bank.account.infrastructure.entity.ReleveEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReleveMapper {

    @Mapping(source = "numeroCompte", target = "numeroCompte")
    @Mapping(source = "date", target = "date")
    @Mapping(source = "soldeActuel", target = "soldeActuel")
    @Mapping(source = "operations", target = "operations")
    @Mapping(source = "typeCompte", target = "typeCompte")
    ReleveEntity mapToEntity(ReleveBancaire releveBancaire);

}
