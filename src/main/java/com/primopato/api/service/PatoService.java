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
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class PatoService {

    private final PatoRepository patoRepository;
    private final PatoAssembler patoAssembler;

    public PatoResponse cadastrar(PatoRequest request) {
        return new PatoResponse(patoRepository.save(patoAssembler.montarPato(request, null)));
    }

    public PatoResponse editar(Long id, PatoRequest request) {
        return new PatoResponse(patoRepository.save(patoAssembler.montarPato(request, getPato(id))));
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
        return Stream.of(EstadoHibernacao.values())
                .map(e -> new DropDownResponse(e.name(), e.getNome()))
                .toList();
    }

    public PatoResponse capturar(Long id) {
        Pato pato = getPato(id);
        pato.setCapturado(true);
        return new PatoResponse(patoRepository.save(pato));
    }
}
