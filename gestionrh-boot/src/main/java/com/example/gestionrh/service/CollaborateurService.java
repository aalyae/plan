package com.example.gestionrh.service;

import com.example.gestionrh.model.Collaborateur;
import com.example.gestionrh.repository.CollaborateurRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollaborateurService {
    private final CollaborateurRepository collaborateurRepository;

    public CollaborateurService(CollaborateurRepository collaborateurRepository) {
        this.collaborateurRepository = collaborateurRepository;
    }

    public List<Collaborateur> findAll() { return collaborateurRepository.findAll(); }
}

