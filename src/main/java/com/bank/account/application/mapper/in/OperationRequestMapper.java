package com.bank.account.application.mapper.in;

import com.bank.account.application.dto.in.OperationDto;
import com.bank.account.domain.model.Operation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OperationRequestMapper {

    @Mapping(source = "typeOperation", target = "typeOperation")
    @Mapping(source = "montant", target = "montant")
    Operation mapOperationDtoToOperation(OperationDto operationDto);

}
