package com.example.gestionrh.repository;

import com.example.gestionrh.model.Horaire;
import com.example.gestionrh.model.Collaborateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface HoraireRepository extends JpaRepository<Horaire, Long> {
    List<Horaire> findByCollaborateur(Collaborateur collaborateur);
    List<Horaire> findByCollaborateurAndDateBetween(Collaborateur collaborateur, LocalDate start, LocalDate end);
}

