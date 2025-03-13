package com.bank.account.application.endpoint;

import com.bank.account.application.controller.CompteApiController;
import com.bank.account.application.dto.in.OperationDto;
import com.bank.account.application.dto.out.ResponseCompteDto;
import com.bank.account.application.dto.out.ResponseReleveDto;
import com.bank.account.domain.model.TypeCompte;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Compte Bancaire API")
public interface CompteApi {

    @Operation(summary = "Ajoute un nouveau compte")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Compte crée avec succès", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CompteApiController.class))}),
            @ApiResponse(responseCode = "400", description = "Données non valides"),
            @ApiResponse(responseCode = "500", description = "une erreur s'est produit")
    })
    @PostMapping(value = "/comptes/{typeCompte}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseCompteDto> creerCompte(@Parameter(name = "Type compte", required = true) @PathVariable TypeCompte typeCompte);

    @Operation(summary = "Fait une opération depot/retrait sur le compte")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Opération enregistrée avec succès", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))}),
            @ApiResponse(responseCode = "404", description = "Ce compte n'existe pas"),
            @ApiResponse(responseCode = "500", description = "une erreur s'est produit")
    })
    @PatchMapping(value = "/comptes/operation/{numeroCompte}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> operation(@Parameter(name = "numeroCompte", required = true) @PathVariable String numeroCompte,
                                   @Parameter(name = "OperationDto", required = true) @Valid @RequestBody OperationDto operationDto);

    @Operation(summary = "Returne le relevé bancaire du compte")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "relevé bancaire", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CompteApiController.class))}),
            @ApiResponse(responseCode = "404", description = "Ce compte n'existe pas"),
            @ApiResponse(responseCode = "500", description = "une erreur s'est produit")
    })
    @GetMapping(value = "/comptes/releve/{numeroCompte}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseReleveDto> releve(@Parameter(name = "numeroCompte", required = true) @PathVariable String numeroCompte);
}

