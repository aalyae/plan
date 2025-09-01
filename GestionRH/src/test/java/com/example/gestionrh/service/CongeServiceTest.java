package com.example.gestionrh.service;

import com.example.gestionrh.model.*;
import com.example.gestionrh.repository.CongeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CongeServiceTest {

    @Test
    void submit_setsStatusPending_andPersists() {
        CongeRepository repo = Mockito.mock(CongeRepository.class);
        CongeService service = new CongeService(repo);
        Collaborateur c = Collaborateur.builder().id(1L).email("a@b.c").build();
        when(repo.save(any(Conge.class))).thenAnswer(inv -> inv.getArgument(0));

        Conge res = service.submitDemande(c, LocalDate.now(), LocalDate.now().plusDays(1), TypeConge.PAYE);

        assertEquals(StatutConge.EN_ATTENTE, res.getStatut());
        ArgumentCaptor<Conge> captor = ArgumentCaptor.forClass(Conge.class);
        verify(repo, times(1)).save(captor.capture());
        assertThat(captor.getValue().getCollaborateur()).isEqualTo(c);
    }
}

