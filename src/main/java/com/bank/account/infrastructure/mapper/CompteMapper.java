package com.bank.account.infrastructure.mapper;

import com.bank.account.domain.model.Compte;
import com.bank.account.domain.model.CompteCourant;
import com.bank.account.domain.model.LivretEpargne;
import com.bank.account.domain.model.TypeCompte;
import com.bank.account.infrastructure.entity.CompteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CompteMapper {

    @Mapping(source = "montantDecouvert", target = "montantDecouvert")
    CompteEntity mapCourantToEntity(CompteCourant compte);

    @Mapping(source = "plafond", target = "plafond")
    CompteEntity mapEpargneToEntity(LivretEpargne livret);

    @Named("mapToEntity")
    default CompteEntity mapToEntity(Compte compte){
        if(TypeCompte.COURANT.toString().equalsIgnoreCase(compte.getTypeCompte().toString())){
            return mapCourantToEntity((CompteCourant) compte);
        }else if(TypeCompte.LIVRET.toString().equalsIgnoreCase(compte.getTypeCompte().toString())){
            return mapEpargneToEntity((LivretEpargne) compte);
        }
        throw new IllegalArgumentException("Type de compte invalide.");
    }

    @Mapping(source = "montantDecouvert", target = "montantDecouvert")
    CompteCourant mapCourantToDomaine(CompteEntity compteEntity);

    @Mapping(source = "plafond", target = "plafond")
    LivretEpargne mapEpargneToDomaine(CompteEntity compteEntity);

    @Named("mapToDomaine")
    default Compte mapToDomaine(CompteEntity compteEntity){
        if(TypeCompte.COURANT.toString().equalsIgnoreCase(compteEntity.getTypeCompte().toString())) {
            return mapCourantToDomaine(compteEntity);
        }else if(TypeCompte.LIVRET.toString().equalsIgnoreCase(compteEntity.getTypeCompte().toString())){
            return mapEpargneToDomaine(compteEntity);
        }
        throw new IllegalArgumentException("Type de compte invalide.");
    }

}
