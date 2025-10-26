package com.primopato.api.service;

import com.primopato.api.entity.Pais;
import com.primopato.api.entity.Pato;
import com.primopato.api.enumerated.EstadoHibernacao;
import com.primopato.api.record.PatoContadorResponse;
import com.primopato.api.record.PatoRequest;
import com.primopato.api.repository.PatoRepository;
import com.primopato.api.service.assembler.PatoAssembler;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PatoService {

    private final PatoRepository patoRepository;
    private final PatoAssembler patoAssembler;
    private final DroneService droneService;
    private final LocalizacaoService localizacaoService;
    private final PaisService paisService;
    private final SuperPoderService superPoderService;
    private final UsuarioService usuarioService;

    public Pato cadastrar(PatoRequest patoRequest, String usuario) {
        log.info("Cadastrando novo pato");
        Pais pais = paisService.obterOuCriarPais(patoRequest.paisDrone());

        return patoRepository.save(
                patoAssembler.montarPato(
                        patoRequest,
                        usuarioService.getUsuario(usuario),
                        droneService.obterOuCriarDrone(patoRequest, pais),
                        temSuperPoder(patoRequest) ? superPoderService.obterOuCriarSuperPoder(patoRequest.nomeSuperPoder(), patoRequest.tipoSuperPoder()) : null,
                        paisService.obterOuCriarPais(patoRequest.paisDrone()),
                        localizacaoService.obterOuCriarLocalizacao(patoRequest)
                )
        );
    }

    public Pato editar(Long id,  PatoRequest patoRequest, String usuario) {
        log.info("Editando pato {}", id);
        Pais pais = paisService.obterOuCriarPais(patoRequest.paisDrone());

        Pato pato = patoAssembler.editarPato(
                patoRequest,
                droneService.obterOuCriarDrone(patoRequest, pais),
                temSuperPoder(patoRequest) ? superPoderService.obterOuCriarSuperPoder(patoRequest.nomeSuperPoder(), patoRequest.tipoSuperPoder()) : null,
                paisService.obterOuCriarPais(patoRequest.paisDrone()),
                localizacaoService.obterOuCriarLocalizacao(patoRequest),
                getPato(id, usuario)
        );
        pato.setMissaoInfo(null);
        return patoRepository.save(pato);
    }

    private boolean temSuperPoder(PatoRequest patoRequest) {
        return patoRequest.estadoHibernacao().equals(EstadoHibernacao.DESPERTO)
                && patoRequest.tipoSuperPoder() != null
                && !StringUtils.isBlank(patoRequest.nomeSuperPoder());
    }

    public void apagar(Long id, String usuario) {
        log.info("Apagando pato {}", id);
        patoRepository.delete(getPato(id, usuario));
    }

    public Pato getPato(Long id, String usuario) {
        log.info("Buscando pato {}", id);
        return patoRepository.findByIdAndUsuario_Usuario(id, usuario)
                .orElseThrow(() -> new EntityNotFoundException("Nenhum pato encontrado com o ID " + id + " para o usuário " + usuario));
    }

    public List<Pato> buscarTodosFiltrado(String filtro, Boolean capturado, String usuario) {
        log.info("Buscando patos");
        boolean todos = capturado == null;
        return patoRepository.findAllByFiltro("%" + filtro.toUpperCase() + "%", todos, Boolean.TRUE.equals(capturado), usuario);
    }

    public Pato capturar(Long id, String usuario) {
        log.info("Definindo pato {} como capturado", id);
        Pato pato = getPato(id, usuario);
        pato.setCapturado(true);
        return patoRepository.save(pato);
    }

    public List<PatoContadorResponse> buscarQuantidadePatosCapturadosENaoCapturados(String usuario) {
        return patoRepository.contarPatosPorStatusCaptura(usuario);
    }
}
