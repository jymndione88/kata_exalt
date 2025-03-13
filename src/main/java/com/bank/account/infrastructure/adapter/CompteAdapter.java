package com.bank.account.infrastructure.adapter;

import com.bank.account.application.exception.ExceptionFonctionnelle;
import com.bank.account.domain.port.out.OutCompte;
import com.bank.account.domain.model.Compte;
import com.bank.account.infrastructure.entity.CompteEntity;
import com.bank.account.infrastructure.mapper.CompteMapper;
import com.bank.account.infrastructure.repository.CompteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class CompteAdapter implements OutCompte {

    private final CompteRepository compteRepository;
    private final CompteMapper compteMapper;

    public CompteAdapter(CompteRepository compteRepository, CompteMapper compteMapper) {
        this.compteRepository = compteRepository;
        this.compteMapper= compteMapper;
    }

    @Override
    public Optional<Compte> findByNumeroCompte(String numeroCompte) {
        return compteRepository.findByNumeroCompte(numeroCompte)
                .map(compteMapper::mapToDomaine);
    }

    @Transactional
    @Override
    public void patchCompte(Compte compte) {
        try {
            CompteEntity c = compteRepository.findByNumeroCompte(compte.getNumeroCompte())
                    .orElseThrow(() -> new EntityNotFoundException("Compte non trouvé"));
            var compteEntity= compteMapper.mapToEntity(compte);
            c.setSolde(compte.getSolde());
            c.setMontantDecouvert(compteEntity.getMontantDecouvert());
            compteRepository.save(c);
            log.info("Solde du compte: {} est mis à jour.", c);
        }catch (Exception ex){
            log.error("Erreur lors de la mise à jour du solde compte." + ex.getMessage());
            throw new ExceptionFonctionnelle("Erreur lors la mise à jour du solde compte.");
        }
    }

    @Transactional
    @Override
    public Optional<Compte> creerCompte(Compte compte) {
        try {
            var compteEntity= compteMapper.mapToEntity(compte);
            compteRepository.save(compteEntity);
            log.info("Compte crée: {}.", compteEntity);
            return Optional.of(compte);
        }catch (Exception ex){
            log.error("Erreur lors de la création du compte." + ex.getMessage());
            throw new ExceptionFonctionnelle("Erreur lors de la création du compte.");
        }
    }

}
