package com.bank.account.application.mapper.out;

import com.bank.account.application.dto.out.ResponseCompteDto;
import com.bank.account.domain.model.Compte;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CompteResponseMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "numeroCompte", target = "numeroCompte")
    @Mapping(source = "solde", target = "solde")
    @Mapping(source = "typeCompte", target = "typeCompte")
    ResponseCompteDto mapCompteToCompteDto(Compte compte);

}
