package com.example.gestionrh.config;

import com.example.gestionrh.model.*;
import com.example.gestionrh.repository.CollaborateurRepository;
import com.example.gestionrh.repository.CongeRepository;
import com.example.gestionrh.repository.HoraireRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalTime;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(CollaborateurRepository collabRepo,
                               HoraireRepository horaireRepo,
                               CongeRepository congeRepo) {
        return args -> {
            if (collabRepo.count() > 0) return;

            Collaborateur alice = collabRepo.save(Collaborateur.builder()
                    .nom("Dupont")
                    .prenom("Alice")
                    .email("alice.dupont@example.com")
                    .poste("Développeuse")
                    .role(Role.ADMIN)
                    .build());

            Collaborateur bob = collabRepo.save(Collaborateur.builder()
                    .nom("Martin")
                    .prenom("Bob")
                    .email("bob.martin@example.com")
                    .poste("Analyste")
                    .role(Role.COLLABORATEUR)
                    .build());

            horaireRepo.save(Horaire.builder()
                    .collaborateur(alice)
                    .date(LocalDate.now())
                    .heureDebut(LocalTime.of(9, 0))
                    .heureFin(LocalTime.of(17, 0))
                    .build());

            horaireRepo.save(Horaire.builder()
                    .collaborateur(bob)
                    .date(LocalDate.now())
                    .heureDebut(LocalTime.of(10, 0))
                    .heureFin(LocalTime.of(18, 0))
                    .build());

            congeRepo.save(Conge.builder()
                    .collaborateur(bob)
                    .dateDebut(LocalDate.now().plusDays(5))
                    .dateFin(LocalDate.now().plusDays(7))
                    .type(TypeConge.PAYE)
                    .statut(StatutConge.EN_ATTENTE)
                    .build());
        };
    }
}

