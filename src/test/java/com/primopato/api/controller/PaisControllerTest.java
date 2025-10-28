package com.primopato.api.controller;

import com.primopato.api.entity.Pais;
import com.primopato.api.record.DropDownResponse;
import com.primopato.api.service.PaisService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaisControllerTest {

    @Test
    void testListarPaises() {
        PaisService paisService = mock(PaisService.class);
        PaisController controller = new PaisController(paisService);

        Pais brasil = new Pais("Brasil");
        brasil.setId(1L);
        Pais argentina = new Pais("Argentina");
        argentina.setId(2L);

        when(paisService.listarPaises()).thenReturn(List.of(brasil, argentina));

        ResponseEntity<List<DropDownResponse>> response = controller.listarPaises();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("1", response.getBody().getFirst().key());
        assertEquals("Brasil", response.getBody().getFirst().value());
        verify(paisService).listarPaises();
    }
}
