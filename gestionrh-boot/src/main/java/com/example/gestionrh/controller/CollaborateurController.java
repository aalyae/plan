package com.example.gestionrh.controller;

import com.example.gestionrh.model.Collaborateur;
import com.example.gestionrh.repository.CollaborateurRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/collaborateurs")
@Tag(name = "Collaborateurs")
public class CollaborateurController {

    private final CollaborateurRepository repository;

    public CollaborateurController(CollaborateurRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Operation(summary = "Liste des collaborateurs")
    public List<Collaborateur> findAll() { return repository.findAll(); }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un collaborateur")
    public Collaborateur get(@PathVariable Long id) { return repository.findById(id).orElseThrow(); }

    @PostMapping
    @Operation(summary = "Créer un collaborateur")
    public Collaborateur create(@RequestBody Collaborateur c) { return repository.save(c); }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un collaborateur")
    public Collaborateur update(@PathVariable Long id, @RequestBody Collaborateur c) {
        c.setId(id);
        return repository.save(c);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un collaborateur")
    public void delete(@PathVariable Long id) { repository.deleteById(id); }
}

