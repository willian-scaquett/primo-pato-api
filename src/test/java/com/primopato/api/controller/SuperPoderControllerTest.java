package com.primopato.api.controller;

import com.primopato.api.entity.SuperPoder;
import com.primopato.api.enumerated.TipoSuperPoder;
import com.primopato.api.record.DropDownResponse;
import com.primopato.api.service.SuperPoderService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SuperPoderControllerTest {

    @Test
    void testListarTipos() {
        SuperPoderService superPoderService = mock(SuperPoderService.class);
        SuperPoderController controller = new SuperPoderController(superPoderService);

        ResponseEntity<List<DropDownResponse>> response = controller.listarTipos();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(TipoSuperPoder.values().length, response.getBody().size());
        assertTrue(response.getBody().stream().anyMatch(r -> r.value() != null));
    }

    @Test
    void testListarSuperPoderes() {
        SuperPoderService superPoderService = mock(SuperPoderService.class);
        SuperPoderController controller = new SuperPoderController(superPoderService);

        SuperPoder sp1 = new SuperPoder();
        sp1.setId(1L);
        sp1.setNome("Telepatia");
        SuperPoder sp2 = new SuperPoder();
        sp2.setId(2L);
        sp2.setNome("Força");

        when(superPoderService.carregarSuperPoderes(TipoSuperPoder.ELETRICIDADE)).thenReturn(List.of(sp1, sp2));

        ResponseEntity<List<DropDownResponse>> response = controller.listarSuperPoderes(TipoSuperPoder.ELETRICIDADE);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("1", response.getBody().getFirst().key());
        assertEquals("Telepatia", response.getBody().getFirst().value());
        verify(superPoderService).carregarSuperPoderes(TipoSuperPoder.ELETRICIDADE);
    }
}
