package com.primopato.api.service;

import com.primopato.api.entity.Pato;
import com.primopato.api.record.PatoRequest;
import com.primopato.api.repository.PatoRepository;
import com.primopato.api.service.assembler.PatoAssembler;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PatoService {

    private final PatoRepository patoRepository;
    private final PatoAssembler patoAssembler;
    private final UsuarioService usuarioService;

    public Pato cadastrar(PatoRequest patoRequest, String usuario) {
        return patoRepository.save(patoAssembler.montarPato(patoRequest, usuarioService.getUsuario(usuario), null));
    }

    public Pato editar(Long id,  PatoRequest request, String usuario) {
        Pato pato = patoAssembler.montarPato(request, usuarioService.getUsuario(usuario), getPato(id, usuario));
        pato.setMissaoInfo(null);
        return patoRepository.save(pato);
    }

    public void apagar(Long id, String usuario) {
        patoRepository.delete(getPato(id, usuario));
    }

    public Pato getPato(Long id, String usuario) {
        return patoRepository.findByIdAndUsuario_Usuario(id, usuario)
                .orElseThrow(() -> new EntityNotFoundException("Nenhum pato encontrado com o ID " + id + " para o usuário " + usuario));
    }

    public List<Pato> buscarTodosFiltrado(String filtro, String usuario) {
        return patoRepository.findAllByFiltro("%" + filtro.toUpperCase() + "%", usuario);
    }

    public Pato capturar(Long id, String usuario) {
        Pato pato = getPato(id, usuario);
        pato.setCapturado(true);
        return patoRepository.save(pato);
    }
}
