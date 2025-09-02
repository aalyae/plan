package com.example.gestionrh.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "horaires")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Horaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Collaborateur collaborateur;

    private LocalDate date;
    private LocalTime heureDebut;
    private LocalTime heureFin;
}

