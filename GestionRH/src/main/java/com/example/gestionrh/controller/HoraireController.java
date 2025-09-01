package com.example.gestionrh.controller;

import com.example.gestionrh.model.Collaborateur;
import com.example.gestionrh.model.Horaire;
import com.example.gestionrh.service.CollaborateurService;
import com.example.gestionrh.service.HoraireService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/horaires")
@Tag(name = "Horaires")
public class HoraireController {

    private final HoraireService horaireService;
    private final CollaborateurService collaborateurService;

    public HoraireController(HoraireService horaireService, CollaborateurService collaborateurService) {
        this.horaireService = horaireService;
        this.collaborateurService = collaborateurService;
    }

    @PostMapping
    @Operation(summary = "Créer un horaire")
    public Horaire create(@Validated @RequestBody Horaire h) { return horaireService.create(h); }

    @GetMapping
    @Operation(summary = "Lister les horaires")
    public List<Horaire> findAll() { return horaireService.findAll(); }

    @GetMapping("/planning/{collaborateurId}")
    @Operation(summary = "Consulter le planning d'un collaborateur")
    public ResponseEntity<List<Horaire>> planning(
            @PathVariable Long collaborateurId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return collaborateurService.findById(collaborateurId)
                .map(c -> ResponseEntity.ok(horaireService.findByCollaborateurAndBetweenDates(c, start, end)))
                .orElse(ResponseEntity.notFound().build());
    }
}

