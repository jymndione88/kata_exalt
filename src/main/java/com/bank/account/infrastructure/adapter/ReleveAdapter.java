package com.bank.account.infrastructure.adapter;

import com.bank.account.application.exception.ExceptionFonctionnelle;
import com.bank.account.domain.model.ReleveBancaire;
import com.bank.account.domain.port.out.OutReleve;
import com.bank.account.infrastructure.mapper.ReleveMapper;
import com.bank.account.infrastructure.repository.ReleveRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReleveAdapter implements OutReleve {

    private final ReleveRepository releveRepository;
    private final ReleveMapper releveMapper;

    public ReleveAdapter(ReleveRepository releveRepository, ReleveMapper releveMapper) {
        this.releveRepository = releveRepository;
        this.releveMapper= releveMapper;
    }

    @Transactional
    @Override
    public void sauvegarderReleve(ReleveBancaire releve) {
        try {
            var releveEntity= releveMapper.mapToEntity(releve);
            releveRepository.save(releveEntity);
            log.info("Rélevé bancaire enregistré: {}.", releveEntity);
        }catch (Exception ex){
            log.error("Erreur lors de l'enregistrement du relévé.");
            throw new ExceptionFonctionnelle("Erreur lors de l'enregistrement du relévé.");
        }

    }
}
