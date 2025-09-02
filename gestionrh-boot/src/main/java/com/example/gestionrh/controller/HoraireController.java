package com.example.gestionrh.controller;

import com.example.gestionrh.model.Horaire;
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

    public HoraireController(HoraireService service) { this.service = service; }

    @GetMapping
    @Operation(summary = "Lister les horaires")
    public List<Horaire> findAll() { return service.findAll(); }

    @GetMapping("/planning/{collaborateurId}")
    @Operation(summary = "Consulter le planning d'un collaborateur")
    public List<Horaire> planning(@PathVariable Long collaborateurId, @RequestParam LocalDate start, @RequestParam LocalDate end) {
        return service.planning(collaborateurId, start, end);
    }
}

