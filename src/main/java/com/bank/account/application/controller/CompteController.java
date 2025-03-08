package com.bank.account.application.controller;

import com.bank.account.domain.exception.AccountException;
import com.bank.account.domain.model.ReleveBancaire;
import com.bank.account.domain.service.CompteService;
import com.bank.account.domain.service.ReleveService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@Tag(name = "Compte Bancaire API")
@RestController
@RequestMapping("/compte")
public class CompteController {

    @Autowired
    private CompteService compteService;

    @Autowired
    private ReleveService releveService;

    @Operation(summary = "Fait le depot du montant sur le compte")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CompteController.class))})
    })
    @PostMapping("/depot/{id}")
    public ResponseEntity<?> deposer(@PathVariable UUID id, @RequestParam BigDecimal montant) {
        try {
             compteService.deposer(id,montant);
            return ResponseEntity.status(HttpStatus.OK).body("Montant déposé avec succès.");
        } catch (AccountException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Fait le retrait du montant du compte")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CompteController.class))})
    })
    @PostMapping("/retrait/{id}")
    public ResponseEntity<?> retirer(@PathVariable UUID id, @RequestParam BigDecimal montant) {
        try {
            compteService.retirer(id, montant);
            return ResponseEntity.status(HttpStatus.OK).body("Montant retiré avec succès.");
        } catch (AccountException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Returne le relevé bancaire")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "relevé bancaire",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CompteController.class))})
    })
    @PostMapping("/releve/{id}")
    public ResponseEntity<?> releve(@PathVariable UUID id){
        try{
            ReleveBancaire releve = releveService.getReleveBancaire(id);
            return ResponseEntity.ok(releve);
        } catch (AccountException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
