package com.primopato.api.service;

import com.primopato.api.entity.Pato;
import com.primopato.api.enumerated.EstadoHibernacao;
import com.primopato.api.record.DropDownResponse;
import com.primopato.api.record.PatoRequest;
import com.primopato.api.record.PatoResponse;
import com.primopato.api.repository.PatoRepository;
import com.primopato.api.service.assembler.PatoAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PatoService {

    private final PatoRepository patoRepository;
    private final PatoAssembler patoAssembler;

    public PatoResponse cadastrar(PatoRequest request) {
        Pato pato = patoAssembler.montarPato(request, null);
        return new PatoResponse(patoRepository.saveAndFlush(pato));
    }

    public PatoResponse editar(Long id, PatoRequest request) {
        Pato patoExistente = patoRepository.findById(id)
                .orElseThrow(() -> new InvalidParameterException("Nenhum pato encontrado com o ID " + id));

        Pato patoAtualizado = patoAssembler.montarPato(request, patoExistente);
        return new PatoResponse(patoRepository.saveAndFlush(patoAtualizado));
    }

    public void apagar(Long id) {
        patoRepository.deleteById(id);
    }

    public PatoRequest buscarPorId(Long id) {
        return new PatoRequest(getPato(id));
    }

    public Pato getPato(Long id) {
        return patoRepository.findById(id).orElseThrow(() -> new InvalidParameterException("Nenhum pato encontrado com o ID " + id));
    }

    public List<PatoResponse> buscarTodosFiltrado(String filtro) {
        return patoRepository.findAllByFiltro("%" + filtro.toUpperCase() + "%");
    }

    public List<DropDownResponse> carregarEstadosHibernacao() {
        return List.of(EstadoHibernacao.values())
                .stream()
                .map(e -> new DropDownResponse(e.name(), e.getNome()))
                .toList();
    }
}
