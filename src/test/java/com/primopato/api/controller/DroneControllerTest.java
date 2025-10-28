package com.primopato.api.controller;

import com.primopato.api.controller.DroneController;
import com.primopato.api.entity.Drone;
import com.primopato.api.entity.FabricanteDrone;
import com.primopato.api.entity.ModeloDrone;
import com.primopato.api.entity.Pais;
import com.primopato.api.record.DropDownResponse;
import com.primopato.api.service.DroneService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DroneControllerTest {

    @Mock
    private DroneService droneService;

    @InjectMocks
    private DroneController droneController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveListarFabricantesComSucesso() {
        // Arrange
        FabricanteDrone fabricante1 = new FabricanteDrone();
        fabricante1.setId(1L);
        fabricante1.setNome("Fabricante 1");
        fabricante1.setPais(new Pais());
        FabricanteDrone fabricante2 = new FabricanteDrone();
        fabricante2.setId(2L);
        fabricante2.setNome("Fabricante 2");
        fabricante2.setPais(new Pais());
        when(droneService.listarFabricantes(10L)).thenReturn(List.of(fabricante1, fabricante2));

        // Act
        ResponseEntity<List<DropDownResponse>> response = droneController.listarFabricantes(10L);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(droneService, times(1)).listarFabricantes(10L);
    }

    @Test
    void deveListarModelosComSucesso() {
        // Arrange
        ModeloDrone modelo1 = new ModeloDrone("12", new FabricanteDrone());
        modelo1.setId(1L);
        ModeloDrone modelo2 = new ModeloDrone("24", new FabricanteDrone());
        modelo2.setId(2L);
        when(droneService.listarModelos(1L)).thenReturn(List.of(modelo1, modelo2));

        // Act
        ResponseEntity<List<DropDownResponse>> response = droneController.listarModelos(1L);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(droneService, times(1)).listarModelos(1L);
    }

    @Test
    void deveListarNumerosSerieComSucesso() {
        // Arrange
        Drone drone1 = new Drone("1", new ModeloDrone());
        drone1.setId(1L);
        Drone drone2 = new Drone("2", new ModeloDrone());
        drone2.setId(2L);
        when(droneService.listarDrones(5L)).thenReturn(List.of(drone1, drone2));

        // Act
        ResponseEntity<List<DropDownResponse>> response = droneController.listarNumerosSerie(5L);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());

        verify(droneService, times(1)).listarDrones(5L);
    }


}
