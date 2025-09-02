package com.example.gestionrh.controller;

import com.example.gestionrh.model.*;
import com.example.gestionrh.repository.CongeRepository;
import com.example.gestionrh.repository.CollaborateurRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/conges")
@Tag(name = "Congés")
public class CongeController {

    private final CongeRepository congeRepository;
    private final CollaborateurRepository collaborateurRepository;

    public CongeController(CongeRepository congeRepository, CollaborateurRepository collaborateurRepository) {
        this.congeRepository = congeRepository;
        this.collaborateurRepository = collaborateurRepository;
    }

    @GetMapping
    @Operation(summary = "Lister tous les congés")
    public List<Conge> findAll() { return congeRepository.findAll(); }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un congé")
    public Conge get(@PathVariable Long id) { return congeRepository.findById(id).orElseThrow(); }

    @PostMapping("/soumettre")
    @Operation(summary = "Soumettre une demande de congé")
    public Conge soumettre(@RequestParam Long collaborateurId,
                           @RequestParam LocalDate dateDebut,
                           @RequestParam LocalDate dateFin,
                           @RequestParam TypeConge type) {
        Collaborateur c = collaborateurRepository.findById(collaborateurId).orElseThrow();
        Conge conge = Conge.builder()
                .collaborateur(c)
                .dateDebut(dateDebut)
                .dateFin(dateFin)
                .type(type)
                .statut(StatutConge.EN_ATTENTE)
                .build();
        return congeRepository.save(conge);
    }

    @PostMapping("/{id}/valider")
    @Operation(summary = "Valider une demande de congé")
    public Conge valider(@PathVariable Long id) {
        Conge c = congeRepository.findById(id).orElseThrow();
        c.setStatut(StatutConge.VALIDE);
        return congeRepository.save(c);
    }

    @PostMapping("/{id}/rejeter")
    @Operation(summary = "Rejeter une demande de congé")
    public Conge rejeter(@PathVariable Long id) {
        Conge c = congeRepository.findById(id).orElseThrow();
        c.setStatut(StatutConge.REJETE);
        return congeRepository.save(c);
    }

    @GetMapping("/historique/{collaborateurId}")
    @Operation(summary = "Historique des congés d'un collaborateur")
    public List<Conge> historique(@PathVariable Long collaborateurId) {
        return congeRepository.findAll().stream()
                .filter(c -> c.getCollaborateur() != null && c.getCollaborateur().getId().equals(collaborateurId))
                .toList();
    }
}

