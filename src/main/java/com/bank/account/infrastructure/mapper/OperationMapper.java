package com.bank.account.infrastructure.mapper;

import com.bank.account.domain.model.Operation;
import com.bank.account.infrastructure.entity.OperationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OperationMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "numeroCompte", target = "numeroCompte")
    @Mapping(source = "dateOperation", target = "dateOperation")
    @Mapping(source = "montant", target = "montant")
    @Mapping(source = "motif", target = "motif")
    @Mapping(source = "typeOperation", target = "typeOperation")
    OperationEntity mapToEntity(Operation operation);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "numeroCompte", target = "numeroCompte")
    @Mapping(source = "dateOperation", target = "dateOperation")
    @Mapping(source = "montant", target = "montant")
    @Mapping(source = "motif", target = "motif")
    @Mapping(source = "typeOperation", target = "typeOperation")
    Operation mapToDomaine(OperationEntity operationEntity);


}
