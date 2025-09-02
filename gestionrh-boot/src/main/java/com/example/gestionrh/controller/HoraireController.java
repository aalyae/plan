package com.example.gestionrh.controller;

import com.example.gestionrh.model.Collaborateur;
import com.example.gestionrh.model.Horaire;
import com.example.gestionrh.repository.CollaborateurRepository;
import com.example.gestionrh.service.HoraireService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/horaires")
@Tag(name = "Horaires")
public class HoraireController {

    private final HoraireService service;
    private final CollaborateurRepository collaborateurRepository;

    public HoraireController(HoraireService service, CollaborateurRepository collaborateurRepository) {
        this.service = service;
        this.collaborateurRepository = collaborateurRepository;
    }

    @GetMapping
    @Operation(summary = "Lister les horaires")
    public List<Horaire> findAll() { return service.findAll(); }

    @GetMapping("/planning/{collaborateurId}")
    @Operation(summary = "Consulter le planning d'un collaborateur")
    public List<Horaire> planning(@PathVariable Long collaborateurId, @RequestParam LocalDate start, @RequestParam LocalDate end) {
        return service.planning(collaborateurId, start, end);
    }

    @PostMapping
    @Operation(summary = "Créer un horaire")
    public Horaire create(@RequestBody CreateHoraireRequest req) {
        Collaborateur c = collaborateurRepository.findById(req.collaborateurId()).orElseThrow();
        Horaire h = Horaire.builder()
                .collaborateur(c)
                .date(req.date())
                .heureDebut(req.heureDebut())
                .heureFin(req.heureFin())
                .build();
        return service.create(h);
    }

    public record CreateHoraireRequest(Long collaborateurId, LocalDate date, java.time.LocalTime heureDebut, java.time.LocalTime heureFin) {}
}

