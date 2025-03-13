package com.bank.account.application.mapper.out;

import com.bank.account.application.dto.out.ResponseReleveDto;
import com.bank.account.domain.model.ReleveBancaire;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReleveResponseMapper {

    @Mapping(source = "numeroCompte", target = "numeroCompte")
    @Mapping(source = "date", target = "date")
    @Mapping(source = "soldeActuel", target = "soldeActuel")
    @Mapping(source = "operations", target = "operations")
    @Mapping(source = "typeCompte", target = "typeCompte")
    ResponseReleveDto mapReleveToReleveDto(ReleveBancaire releveBancaire);

}
