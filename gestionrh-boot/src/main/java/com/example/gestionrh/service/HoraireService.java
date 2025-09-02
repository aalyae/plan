package com.example.gestionrh.service;

import com.example.gestionrh.model.Horaire;
import com.example.gestionrh.repository.HoraireRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HoraireService {
    private final HoraireRepository horaireRepository;

    public HoraireService(HoraireRepository horaireRepository) {
        this.horaireRepository = horaireRepository;
    }

    public List<Horaire> findAll() { return horaireRepository.findAll(); }

    public List<Horaire> planning(Long collaborateurId, LocalDate start, LocalDate end) {
        return horaireRepository.findAll().stream()
                .filter(h -> h.getCollaborateur() != null && h.getCollaborateur().getId().equals(collaborateurId))
                .filter(h -> !h.getDate().isBefore(start) && !h.getDate().isAfter(end))
                .collect(Collectors.toList());
    }

    public Horaire create(Horaire horaire) { return horaireRepository.save(horaire); }
}

