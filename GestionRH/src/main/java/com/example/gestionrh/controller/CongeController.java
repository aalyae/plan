package com.example.gestionrh.controller;

import com.example.gestionrh.model.*;
import com.example.gestionrh.service.CollaborateurService;
import com.example.gestionrh.service.CongeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/conges")
@Tag(name = "Congés")
public class CongeController {

    private final CongeService congeService;
    private final CollaborateurService collaborateurService;

    public CongeController(CongeService congeService, CollaborateurService collaborateurService) {
        this.congeService = congeService;
        this.collaborateurService = collaborateurService;
    }

    @GetMapping
    @Operation(summary = "Lister tous les congés")
    public List<Conge> findAll() { return congeService.findAll(); }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un congé")
    public ResponseEntity<Conge> get(@PathVariable Long id) { return congeService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()); }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un congé")
    public ResponseEntity<Void> delete(@PathVariable Long id) { congeService.delete(id); return ResponseEntity.noContent().build(); }

    @PostMapping("/soumettre")
    @Operation(summary = "Soumettre une demande de congé")
    public ResponseEntity<Conge> soumettre(
            @RequestParam Long collaborateurId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin,
            @RequestParam TypeConge type) {
        return collaborateurService.findById(collaborateurId)
                .map(c -> ResponseEntity.ok(congeService.submitDemande(c, dateDebut, dateFin, type)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/valider")
    @Operation(summary = "Valider une demande de congé")
    public Conge valider(@PathVariable Long id) { return congeService.valider(id); }

    @PostMapping("/{id}/rejeter")
    @Operation(summary = "Rejeter une demande de congé")
    public Conge rejeter(@PathVariable Long id) { return congeService.rejeter(id); }

    @GetMapping("/historique/{collaborateurId}")
    @Operation(summary = "Historique des congés d'un collaborateur")
    public ResponseEntity<List<Conge>> historique(@PathVariable Long collaborateurId) {
        return collaborateurService.findById(collaborateurId)
                .map(c -> ResponseEntity.ok(congeService.historiqueCollaborateur(c)))
                .orElse(ResponseEntity.notFound().build());
    }
}

