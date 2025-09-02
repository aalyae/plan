package com.example.gestionrh.controller;

import com.example.gestionrh.model.Collaborateur;
import com.example.gestionrh.service.CollaborateurService;
import com.example.gestionrh.service.CongeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@Tag(name = "Dashboard")
public class DashboardController {

    private final CollaborateurService collaborateurService;
    private final CongeService congeService;

    public DashboardController(CollaborateurService collaborateurService, CongeService congeService) {
        this.collaborateurService = collaborateurService;
        this.congeService = congeService;
    }

    @GetMapping("/disponibilite")
    @Operation(summary = "Consulter la disponibilité globale des collaborateurs")
    public ResponseEntity<Map<String, Object>> disponibiliteGlobale() {
        List<Collaborateur> collaborateurs = collaborateurService.findAll();
        LocalDate today = LocalDate.now();
        long enConge = collaborateurs.stream().filter(c -> congeService.estEnConge(c, today)).count();
        long disponibles = collaborateurs.size() - enConge;
        Map<String, Object> result = new HashMap<>();
        result.put("date", today);
        result.put("total", collaborateurs.size());
        result.put("disponibles", disponibles);
        result.put("enConge", enConge);
        return ResponseEntity.ok(result);
    }
}

