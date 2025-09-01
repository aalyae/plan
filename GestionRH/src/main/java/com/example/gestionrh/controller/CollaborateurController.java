package com.example.gestionrh.controller;

import com.example.gestionrh.model.Collaborateur;
import com.example.gestionrh.service.CollaborateurService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/collaborateurs")
@Tag(name = "Collaborateurs")
public class CollaborateurController {

    private final CollaborateurService collaborateurService;

    public CollaborateurController(CollaborateurService collaborateurService) {
        this.collaborateurService = collaborateurService;
    }

    @PostMapping
    @Operation(summary = "Créer un collaborateur")
    public ResponseEntity<Collaborateur> create(@Validated @RequestBody Collaborateur c) {
        Collaborateur saved = collaborateurService.create(c);
        return ResponseEntity.created(URI.create("/api/collaborateurs/" + saved.getId())).body(saved);
    }

    @GetMapping
    @Operation(summary = "Liste des collaborateurs")
    public List<Collaborateur> findAll() { return collaborateurService.findAll(); }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un collaborateur")
    public ResponseEntity<Collaborateur> get(@PathVariable Long id) {
        return collaborateurService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un collaborateur")
    public Collaborateur update(@PathVariable Long id, @Validated @RequestBody Collaborateur c) {
        return collaborateurService.update(id, c);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un collaborateur")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        collaborateurService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

