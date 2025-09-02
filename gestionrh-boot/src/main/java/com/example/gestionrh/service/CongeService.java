package com.example.gestionrh.service;

import com.example.gestionrh.model.Collaborateur;
import com.example.gestionrh.model.Conge;
import com.example.gestionrh.repository.CongeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CongeService {
    private final CongeRepository congeRepository;

    public CongeService(CongeRepository congeRepository) {
        this.congeRepository = congeRepository;
    }

    public boolean estEnConge(Collaborateur c, LocalDate day) {
        return congeRepository.findAll().stream()
                .filter(x -> x.getCollaborateur() != null && x.getCollaborateur().getId().equals(c.getId()))
                .anyMatch(x -> !day.isBefore(x.getDateDebut()) && !day.isAfter(x.getDateFin()));
    }
}

