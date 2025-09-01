package com.example.gestionrh.service;

import com.example.gestionrh.model.*;
import com.example.gestionrh.repository.CongeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CongeService {

    private final CongeRepository congeRepository;

    public CongeService(CongeRepository congeRepository) {
        this.congeRepository = congeRepository;
    }

    public Conge submitDemande(Collaborateur collaborateur, LocalDate debut, LocalDate fin, TypeConge type) {
        Conge conge = Conge.builder()
                .collaborateur(collaborateur)
                .dateDebut(debut)
                .dateFin(fin)
                .type(type)
                .statut(StatutConge.EN_ATTENTE)
                .build();
        return congeRepository.save(conge);
    }

    public Conge valider(Long congeId) {
        Conge conge = congeRepository.findById(congeId)
                .orElseThrow(() -> new IllegalArgumentException("Congé introuvable: " + congeId));
        conge.setStatut(StatutConge.VALIDE);
        return congeRepository.save(conge);
    }

    public Conge rejeter(Long congeId) {
        Conge conge = congeRepository.findById(congeId)
                .orElseThrow(() -> new IllegalArgumentException("Congé introuvable: " + congeId));
        conge.setStatut(StatutConge.REJETE);
        return congeRepository.save(conge);
    }

    @Transactional(readOnly = true)
    public List<Conge> historiqueCollaborateur(Collaborateur collaborateur) {
        return congeRepository.findByCollaborateur(collaborateur);
    }

    @Transactional(readOnly = true)
    public boolean estEnConge(Collaborateur c, LocalDate date) {
        return !congeRepository
                .findByCollaborateurAndDateDebutLessThanEqualAndDateFinGreaterThanEqual(c, date, date)
                .isEmpty();
    }

    public void delete(Long id) { congeRepository.deleteById(id); }

    @Transactional(readOnly = true)
    public List<Conge> findAll() { return congeRepository.findAll(); }

    @Transactional(readOnly = true)
    public Optional<Conge> findById(Long id) { return congeRepository.findById(id); }
}

