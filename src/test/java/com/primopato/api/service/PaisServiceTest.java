package com.primopato.api.service;

import com.primopato.api.entity.Pais;
import com.primopato.api.repository.PaisRepository;
import com.primopato.api.utils.CustomStringUtils;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mockStatic;

class PaisServiceTest {

    @Test
    void testListarPaises() {
        PaisRepository paisRepository = mock(PaisRepository.class);
        PaisService service = new PaisService(paisRepository);

        List<Pais> lista = List.of(new Pais("Brasil"), new Pais("Argentina"));
        when(paisRepository.findAllByOrderByNomeAsc()).thenReturn(lista);

        List<Pais> resultado = service.listarPaises();

        assertEquals(2, resultado.size());
        assertEquals("Brasil", resultado.get(0).getNome());
        verify(paisRepository).findAllByOrderByNomeAsc();
    }

    @Test
    void testObterOuCriarPais_Existente() {
        PaisRepository paisRepository = mock(PaisRepository.class);
        PaisService service = new PaisService(paisRepository);

        String nomeEntrada = "brasil";
        String nomeFormatado = "Brasil";
        Pais existente = new Pais(nomeFormatado);

        try (var mocked = mockStatic(CustomStringUtils.class)) {
            mocked.when(() -> CustomStringUtils.formataIncialMaiuscula(nomeEntrada)).thenReturn(nomeFormatado);
            when(paisRepository.findByNome(nomeFormatado)).thenReturn(Optional.of(existente));

            Pais resultado = service.obterOuCriarPais(nomeEntrada);

            assertSame(existente, resultado);
            verify(paisRepository).findByNome(nomeFormatado);
        }
    }

    @Test
    void testObterOuCriarPais_Novo() {
        PaisRepository paisRepository = mock(PaisRepository.class);
        PaisService service = new PaisService(paisRepository);

        String nomeEntrada = "canada";
        String nomeFormatado = "Canada";
        Pais novo = new Pais(nomeFormatado);

        try (var mocked = mockStatic(CustomStringUtils.class)) {
            mocked.when(() -> CustomStringUtils.formataIncialMaiuscula(nomeEntrada)).thenReturn(nomeFormatado);
            when(paisRepository.findByNome(nomeFormatado)).thenReturn(Optional.empty());
            when(paisRepository.save(any(Pais.class))).thenReturn(novo);

            Pais resultado = service.obterOuCriarPais(nomeEntrada);

            assertEquals("Canada", resultado.getNome());
            verify(paisRepository).findByNome(nomeFormatado);
            verify(paisRepository).save(any(Pais.class));
        }
    }
}
