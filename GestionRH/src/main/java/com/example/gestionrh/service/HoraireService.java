package com.example.gestionrh.service;

import com.example.gestionrh.model.Collaborateur;
import com.example.gestionrh.model.Horaire;
import com.example.gestionrh.repository.HoraireRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HoraireService {

    private final HoraireRepository horaireRepository;

    public HoraireService(HoraireRepository horaireRepository) {
        this.horaireRepository = horaireRepository;
    }

    public Horaire create(Horaire h) { return horaireRepository.save(h); }

    @Transactional(readOnly = true)
    public List<Horaire> findAll() { return horaireRepository.findAll(); }

    @Transactional(readOnly = true)
    public Optional<Horaire> findById(Long id) { return horaireRepository.findById(id); }

    @Transactional(readOnly = true)
    public List<Horaire> findByCollaborateur(Collaborateur c) { return horaireRepository.findByCollaborateur(c); }

    @Transactional(readOnly = true)
    public List<Horaire> findByCollaborateurAndBetweenDates(Collaborateur c, LocalDate start, LocalDate end) {
        return horaireRepository.findByCollaborateurAndDateBetween(c, start, end);
    }

    public Horaire update(Long id, Horaire updated) {
        return horaireRepository.findById(id).map(existing -> {
            existing.setDate(updated.getDate());
            existing.setHeureDebut(updated.getHeureDebut());
            existing.setHeureFin(updated.getHeureFin());
            return horaireRepository.save(existing);
        }).orElseThrow(() -> new IllegalArgumentException("Horaire introuvable: " + id));
    }

    public void delete(Long id) { horaireRepository.deleteById(id); }
}

