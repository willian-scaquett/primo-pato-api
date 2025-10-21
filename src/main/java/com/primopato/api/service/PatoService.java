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
    private final UsuarioService usuarioService;

    public PatoResponse cadastrar(PatoRequest request, String usuario) {
        return new PatoResponse(patoRepository.save(patoAssembler.montarPato(request, usuarioService.getUsuario(usuario), null)));
    }

    public Pato editar(Long id,  PatoRequest request, String usuario) {
        return patoRepository.save(patoAssembler.montarPato(request, usuarioService.getUsuario(usuario), getPato(id, usuario)));
    }

    public void apagar(Long id, String usuario) {
        patoRepository.delete(getPato(id, usuario));
    }

    public PatoRequest buscarPorId(Long id, String usuario) {
        return new PatoRequest(getPato(id, usuario));
    }

    public Pato getPato(Long id, String usuario) {
        return patoRepository.findByIdAndUsuario_Usuario(id, usuario)
                .orElseThrow(() -> new InvalidParameterException("Nenhum pato encontrado com o ID " + id + " para o usuário " + usuario));
    }

    public List<PatoResponse> buscarTodosFiltrado(String filtro, String usuario) {
        return patoRepository.findAllByFiltro("%" + filtro.toUpperCase() + "%", usuario);
    }

    public List<DropDownResponse> carregarEstadosHibernacao() {
        return Stream.of(EstadoHibernacao.values())
                .map(e -> new DropDownResponse(e.name(), e.getNome()))
                .toList();
    }

    public PatoResponse capturar(Long id, String usuario) {
        Pato pato = getPato(id, usuario);
        pato.setCapturado(true);
        return new PatoResponse(patoRepository.save(pato));
    }
}
