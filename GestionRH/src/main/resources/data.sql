-- Seed collaborators
INSERT INTO collaborateurs (id, nom, prenom, email, poste, role) VALUES
 (1, 'Dupont', 'Alice', 'alice.dupont@example.com', 'Développeuse', 'ADMIN'),
 (2, 'Martin', 'Bob', 'bob.martin@example.com', 'Analyste', 'COLLABORATEUR');

-- Seed horaires
INSERT INTO horaires (id, collaborateur_id, date, heure_debut, heure_fin) VALUES
 (1, 1, DATE '2025-01-02', TIME '09:00:00', TIME '17:00:00'),
 (2, 2, DATE '2025-01-02', TIME '10:00:00', TIME '18:00:00');

-- Seed conges
INSERT INTO conges (id, collaborateur_id, date_debut, date_fin, type, statut) VALUES
 (1, 2, DATE '2025-01-10', DATE '2025-01-12', 'PAYE', 'EN_ATTENTE');

