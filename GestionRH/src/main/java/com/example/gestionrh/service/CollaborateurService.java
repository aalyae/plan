package com.example.gestionrh.service;

import com.example.gestionrh.model.Collaborateur;
import com.example.gestionrh.repository.CollaborateurRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CollaborateurService {

    private final CollaborateurRepository collaborateurRepository;

    public CollaborateurService(CollaborateurRepository collaborateurRepository) {
        this.collaborateurRepository = collaborateurRepository;
    }

    public Collaborateur create(Collaborateur c) {
        return collaborateurRepository.save(c);
    }

    @Transactional(readOnly = true)
    public List<Collaborateur> findAll() {
        return collaborateurRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Collaborateur> findById(Long id) {
        return collaborateurRepository.findById(id);
    }

    public Collaborateur update(Long id, Collaborateur updated) {
        return collaborateurRepository.findById(id)
                .map(existing -> {
                    existing.setNom(updated.getNom());
                    existing.setPrenom(updated.getPrenom());
                    existing.setEmail(updated.getEmail());
                    existing.setPoste(updated.getPoste());
                    existing.setRole(updated.getRole());
                    return collaborateurRepository.save(existing);
                })
                .orElseThrow(() -> new IllegalArgumentException("Collaborateur introuvable: " + id));
    }

    public void delete(Long id) {
        collaborateurRepository.deleteById(id);
    }
}

