package com.bank.account.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AccountExceptions {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> typeOperationInvalideException(Exception ex) {
        Map<String, Object> erreurs = new HashMap<>();
        erreurs.put("timestamp", LocalDateTime.now());
        erreurs.put("statut", HttpStatus.BAD_REQUEST.value());
        erreurs.put("erreur", ex.getMessage() );
        erreurs.put("message", "Requete/ type operation invalide.");
        return erreurs;
    }

    @ExceptionHandler(ExceptionFonctionnelle.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> fonctionnelleException(ExceptionFonctionnelle ex) {
        Map<String, Object> erreurs = new HashMap<>();
        erreurs.put("timestamp", LocalDateTime.now());
        erreurs.put("statut", HttpStatus.BAD_REQUEST.value());
        erreurs.put("erreur", "Erreur fonctionnelle");
        erreurs.put("message", ex.getMessage());
        return erreurs;
    }
}
