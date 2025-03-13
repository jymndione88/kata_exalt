package com.bank.account.application.controller;

import com.bank.account.application.dto.in.OperationDto;
import com.bank.account.application.dto.out.ResponseCompteDto;
import com.bank.account.application.dto.out.ResponseReleveDto;
import com.bank.account.application.endpoint.CompteApi;
import com.bank.account.application.mapper.in.OperationRequestMapper;
import com.bank.account.application.mapper.out.CompteResponseMapper;
import com.bank.account.application.mapper.out.ReleveResponseMapper;
import com.bank.account.domain.model.Compte;
import com.bank.account.domain.model.ReleveBancaire;
import com.bank.account.domain.model.TypeCompte;
import com.bank.account.domain.port.in.InCompte;
import com.bank.account.domain.port.in.InReleve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
public class CompteApiController implements CompteApi {

    @Autowired
    MessageSource messageSource;

    private final InCompte inCompte;
    private final InReleve inReleve;
    private final OperationRequestMapper operationRequestMapper;
    private final ReleveResponseMapper releveResponseMapper;
    private final CompteResponseMapper compteResponseMapper;

    public CompteApiController(@Qualifier("CompteService") InCompte inCompte, @Qualifier("ReleveService") InReleve inReleve, OperationRequestMapper operationRequestMapper, ReleveResponseMapper releveResponseMapper, CompteResponseMapper compteResponseMapper) {
        this.inCompte = inCompte;
        this.inReleve = inReleve;
        this.operationRequestMapper = operationRequestMapper;
        this.releveResponseMapper = releveResponseMapper;
        this.compteResponseMapper= compteResponseMapper;
    }

    @Override
    public ResponseEntity<ResponseCompteDto> creerCompte(TypeCompte typeCompte) {
        Compte compte= inCompte.creerCompte(typeCompte);
        var compteDto= compteResponseMapper.mapCompteToCompteDto(compte);
        return ResponseEntity.ok(compteDto);
    }

    @Override
    public ResponseEntity<String> operation(String numeroCompte, OperationDto operationDto) {
        var operation = operationRequestMapper.mapOperationDtoToOperation(operationDto);
        operation.setNumeroCompte(numeroCompte);
        inCompte.operation(operation);
        String message= messageSource.getMessage("message.succes.generique", new Object[]{operationDto.getTypeOperation(),operationDto.getMontant(),numeroCompte}, Locale.getDefault());
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @Override
    public ResponseEntity<ResponseReleveDto> releve(String numeroCompte) {
        ReleveBancaire releve = inReleve.getReleveBancaire(numeroCompte);
        var releveDto= releveResponseMapper.mapReleveToReleveDto(releve);
        return ResponseEntity.ok(releveDto);
    }
}
