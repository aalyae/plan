package com.example.gestionrh.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "collaborateurs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Collaborateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String poste;

    @Enumerated(EnumType.STRING)
    private Role role;
}

