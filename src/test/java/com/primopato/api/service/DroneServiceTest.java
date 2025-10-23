package com.primopato.api.service;

import com.primopato.api.entity.Drone;
import com.primopato.api.entity.FabricanteDrone;
import com.primopato.api.entity.ModeloDrone;
import com.primopato.api.entity.Pais;
import com.primopato.api.enumerated.EstadoHibernacao;
import com.primopato.api.record.PatoRequest;
import com.primopato.api.repository.DroneRepository;
import com.primopato.api.repository.FabricanteDroneRepository;
import com.primopato.api.repository.ModeloDroneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("DroneService - Testes Unitários")
class DroneServiceTest {

    @Mock
    private DroneRepository droneRepository;

    @Mock
    private FabricanteDroneRepository fabricanteDroneRepository;

    @Mock
    private ModeloDroneRepository modeloDroneRepository;

    @InjectMocks
    private DroneService droneService;

    @Captor
    private ArgumentCaptor<Drone> droneCaptor;

    @Captor
    private ArgumentCaptor<FabricanteDrone> fabricanteCaptor;

    @Captor
    private ArgumentCaptor<ModeloDrone> modeloCaptor;

    private PatoRequest patoRequest;
    private Pais pais;
    private FabricanteDrone fabricante;
    private ModeloDrone modelo;
    private Drone drone;

    @BeforeEach
    void setUp() {
        pais = new Pais();
        pais.setId(1L);
        pais.setNome("China");

        fabricante = new FabricanteDrone("DJI", pais);
        fabricante.setId(1L);

        modelo = new ModeloDrone("Phantom 4", fabricante);
        modelo.setId(1L);

        drone = new Drone("DRONE-001", modelo);
        drone.setId(1L);

        patoRequest = new PatoRequest(
                "drone-001",
                "phantom 4",
                "dji",
                "China",
                15.5f,
                2.3f,
                -23.550520,
                -46.633308,
                "Brasil",
                "São Paulo",
                "São Paulo",
                "Próximo ao lago",
                "Av. Paulista, 1000",
                0.5f,
                EstadoHibernacao.DESPERTO,
                120,
                0,
                null,
                null,
                false
        );
    }

    @Test
    void deveRetornarDroneExistenteQuandoEncontrado() {
        when(fabricanteDroneRepository.findByNomeAndPais("Dji", pais))
                .thenReturn(Optional.of(fabricante));
        when(modeloDroneRepository.findByNomeAndFabricante("Phantom 4", fabricante))
                .thenReturn(Optional.of(modelo));
        when(droneRepository.findByNumeroSerieAndModelo("DRONE-001", modelo))
                .thenReturn(Optional.of(drone));

        Drone resultado = droneService.obterOuCriarDrone(patoRequest, pais);

        assertNotNull(resultado);
        assertEquals(drone, resultado);
        assertEquals("DRONE-001", resultado.getNumeroSerie());
        verify(droneRepository, never()).save(any(Drone.class));
    }

    @Test
    void deveCriarNovoDroneQuandoNaoEncontrado() {
        when(fabricanteDroneRepository.findByNomeAndPais("Dji", pais))
                .thenReturn(Optional.of(fabricante));
        when(modeloDroneRepository.findByNomeAndFabricante("Phantom 4", fabricante))
                .thenReturn(Optional.of(modelo));
        when(droneRepository.findByNumeroSerieAndModelo("DRONE-001", modelo))
                .thenReturn(Optional.empty());
        when(droneRepository.save(any(Drone.class))).thenReturn(drone);

        Drone resultado = droneService.obterOuCriarDrone(patoRequest, pais);

        assertNotNull(resultado);
        verify(droneRepository, times(1)).save(droneCaptor.capture());

        Drone droneSalvo = droneCaptor.getValue();
        assertEquals("DRONE-001", droneSalvo.getNumeroSerie());
        assertEquals(modelo, droneSalvo.getModelo());
    }

    @Test
    void deveConverterNumeroSerieParaMaiusculas() {
        when(fabricanteDroneRepository.findByNomeAndPais(any(), any()))
                .thenReturn(Optional.of(fabricante));
        when(modeloDroneRepository.findByNomeAndFabricante(any(), any()))
                .thenReturn(Optional.of(modelo));
        when(droneRepository.findByNumeroSerieAndModelo("DRONE-001", modelo))
                .thenReturn(Optional.of(drone));

        droneService.obterOuCriarDrone(patoRequest, pais);

        verify(droneRepository).findByNumeroSerieAndModelo("DRONE-001", modelo);
    }

    @Test
    void deveCriarFabricanteEModeloAoCriarDrone() {
        when(fabricanteDroneRepository.findByNomeAndPais("Dji", pais))
                .thenReturn(Optional.empty());
        when(fabricanteDroneRepository.save(any(FabricanteDrone.class)))
                .thenReturn(fabricante);
        when(modeloDroneRepository.findByNomeAndFabricante("Phantom 4", fabricante))
                .thenReturn(Optional.empty());
        when(modeloDroneRepository.save(any(ModeloDrone.class)))
                .thenReturn(modelo);
        when(droneRepository.findByNumeroSerieAndModelo("DRONE-001", modelo))
                .thenReturn(Optional.empty());
        when(droneRepository.save(any(Drone.class))).thenReturn(drone);

        Drone resultado = droneService.obterOuCriarDrone(patoRequest, pais);

        assertNotNull(resultado);
        verify(fabricanteDroneRepository).save(any(FabricanteDrone.class));
        verify(modeloDroneRepository).save(any(ModeloDrone.class));
        verify(droneRepository).save(any(Drone.class));
    }

    @Test
    void deveRetornarFabricanteExistenteQuandoEncontrado() {
        when(fabricanteDroneRepository.findByNomeAndPais("Dji", pais))
                .thenReturn(Optional.of(fabricante));

        FabricanteDrone resultado = droneService.obterOuCriarFabricante("dji", pais);

        assertNotNull(resultado);
        assertEquals(fabricante, resultado);
        assertEquals("DJI", resultado.getNome());
        verify(fabricanteDroneRepository, never()).save(any(FabricanteDrone.class));
    }

    @Test
    void deveCriarNovoFabricanteQuandoNaoEncontrado() {
        when(fabricanteDroneRepository.findByNomeAndPais("Parrot", pais))
                .thenReturn(Optional.empty());

        FabricanteDrone novoFabricante = new FabricanteDrone("Parrot", pais);
        when(fabricanteDroneRepository.save(any(FabricanteDrone.class)))
                .thenReturn(novoFabricante);

        FabricanteDrone resultado = droneService.obterOuCriarFabricante("parrot", pais);

        assertNotNull(resultado);
        verify(fabricanteDroneRepository, times(1)).save(fabricanteCaptor.capture());

        FabricanteDrone fabricanteSalvo = fabricanteCaptor.getValue();
        assertEquals("Parrot", fabricanteSalvo.getNome());
        assertEquals(pais, fabricanteSalvo.getPais());
    }

    @Test
    void deveFormatarNomeFabricanteParaInicialMaiuscula() {
        when(fabricanteDroneRepository.findByNomeAndPais("Dji", pais))
                .thenReturn(Optional.of(fabricante));

        droneService.obterOuCriarFabricante("dji", pais);

        verify(fabricanteDroneRepository).findByNomeAndPais("Dji", pais);
    }

    @Test
    void deveAssociarPaisAoCriarFabricante() {
        when(fabricanteDroneRepository.findByNomeAndPais("Skydio", pais))
                .thenReturn(Optional.empty());
        when(fabricanteDroneRepository.save(any(FabricanteDrone.class)))
                .thenReturn(new FabricanteDrone("Skydio", pais));

        droneService.obterOuCriarFabricante("skydio", pais);

        verify(fabricanteDroneRepository).save(fabricanteCaptor.capture());
        FabricanteDrone fabricanteSalvo = fabricanteCaptor.getValue();
        assertEquals(pais, fabricanteSalvo.getPais());
    }

    @Test
    void deveRetornarModeloExistenteQuandoEncontrado() {
        when(modeloDroneRepository.findByNomeAndFabricante("Phantom 4", fabricante))
                .thenReturn(Optional.of(modelo));

        ModeloDrone resultado = droneService.obterOuCriarModelo("phantom 4", fabricante);

        assertNotNull(resultado);
        assertEquals(modelo, resultado);
        assertEquals("Phantom 4", resultado.getNome());
        verify(modeloDroneRepository, never()).save(any(ModeloDrone.class));
    }

    @Test
    void deveCriarNovoModeloQuandoNaoEncontrado() {
        when(modeloDroneRepository.findByNomeAndFabricante("Mavic Pro", fabricante))
                .thenReturn(Optional.empty());

        ModeloDrone novoModelo = new ModeloDrone("Mavic Pro", fabricante);
        when(modeloDroneRepository.save(any(ModeloDrone.class)))
                .thenReturn(novoModelo);

        ModeloDrone resultado = droneService.obterOuCriarModelo("mavic pro", fabricante);

        assertNotNull(resultado);
        verify(modeloDroneRepository, times(1)).save(modeloCaptor.capture());

        ModeloDrone modeloSalvo = modeloCaptor.getValue();
        assertEquals("Mavic Pro", modeloSalvo.getNome());
        assertEquals(fabricante, modeloSalvo.getFabricante());
    }

    @Test
    void deveFormatarNomeModeloParaInicialMaiuscula() {
        when(modeloDroneRepository.findByNomeAndFabricante("Phantom 4", fabricante))
                .thenReturn(Optional.of(modelo));

        droneService.obterOuCriarModelo("phantom 4", fabricante);

        verify(modeloDroneRepository).findByNomeAndFabricante("Phantom 4", fabricante);
    }

    @Test
    void deveAssociarFabricanteAoCriarModelo() {
        when(modeloDroneRepository.findByNomeAndFabricante("Inspire 2", fabricante))
                .thenReturn(Optional.empty());
        when(modeloDroneRepository.save(any(ModeloDrone.class)))
                .thenReturn(new ModeloDrone("Inspire 2", fabricante));

        droneService.obterOuCriarModelo("inspire 2", fabricante);

        verify(modeloDroneRepository).save(modeloCaptor.capture());
        ModeloDrone modeloSalvo = modeloCaptor.getValue();
        assertEquals(fabricante, modeloSalvo.getFabricante());
    }

    @Test
    void deveListarFabricantesPorIdPais() {
        FabricanteDrone fabricante2 = new FabricanteDrone("Parrot", pais);
        List<FabricanteDrone> fabricantes = Arrays.asList(fabricante, fabricante2);

        when(fabricanteDroneRepository.findAllByPais_Id(1L))
                .thenReturn(fabricantes);

        List<FabricanteDrone> resultado = droneService.listarFabricantes(1L);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertThat(resultado).contains(fabricante, fabricante2);
        verify(fabricanteDroneRepository).findAllByPais_Id(1L);
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverFabricantes() {
        when(fabricanteDroneRepository.findAllByPais_Id(999L))
                .thenReturn(List.of());

        List<FabricanteDrone> resultado = droneService.listarFabricantes(999L);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(fabricanteDroneRepository).findAllByPais_Id(999L);
    }

    @Test
    void deveListarModelosPorIdFabricante() {
        ModeloDrone modelo2 = new ModeloDrone("Mavic Pro", fabricante);
        List<ModeloDrone> modelos = Arrays.asList(modelo, modelo2);

        when(modeloDroneRepository.findAllByFabricante_Id(1L))
                .thenReturn(modelos);

        List<ModeloDrone> resultado = droneService.listarModelos(1L);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertThat(resultado).contains(modelo, modelo2);
        verify(modeloDroneRepository).findAllByFabricante_Id(1L);
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverModelos() {
        when(modeloDroneRepository.findAllByFabricante_Id(999L))
                .thenReturn(List.of());

        List<ModeloDrone> resultado = droneService.listarModelos(999L);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(modeloDroneRepository).findAllByFabricante_Id(999L);
    }

    @Test
    void deveListarDronesPorIdModelo() {
        Drone drone2 = new Drone("DRONE-002", modelo);
        List<Drone> drones = Arrays.asList(drone, drone2);

        when(droneRepository.findAllByModelo_Id(1L))
                .thenReturn(drones);

        List<Drone> resultado = droneService.listarDrones(1L);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertThat(resultado).contains(drone, drone2);
        verify(droneRepository).findAllByModelo_Id(1L);
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverDrones() {
        when(droneRepository.findAllByModelo_Id(999L))
                .thenReturn(List.of());

        List<Drone> resultado = droneService.listarDrones(999L);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(droneRepository).findAllByModelo_Id(999L);
    }
}