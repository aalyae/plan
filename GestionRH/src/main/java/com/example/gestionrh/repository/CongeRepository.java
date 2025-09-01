package com.example.gestionrh.repository;

import com.example.gestionrh.model.Conge;
import com.example.gestionrh.model.Collaborateur;
import com.example.gestionrh.model.StatutConge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CongeRepository extends JpaRepository<Conge, Long> {
    List<Conge> findByCollaborateur(Collaborateur collaborateur);
    List<Conge> findByCollaborateurAndDateDebutLessThanEqualAndDateFinGreaterThanEqual(Collaborateur collaborateur, LocalDate end, LocalDate start);
    List<Conge> findByStatut(StatutConge statut);
}

