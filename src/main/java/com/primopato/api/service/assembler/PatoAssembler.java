package com.primopato.api.service.assembler;

import com.primopato.api.entity.*;
import com.primopato.api.enumerated.EstadoHibernacao;
import com.primopato.api.record.PatoRequest;
import com.primopato.api.utils.LocalizacaoUtils;
import com.primopato.api.utils.UnidadesUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PatoAssembler {

    public Pato montarPato(PatoRequest patoRequest, Usuario usuario, Drone drone, SuperPoder superPoder, Pais pais, Localizacao localizacao) {
        Pato pato = new Pato();
        pato.setUsuario(usuario);
        return definirPato(
                patoRequest,
                drone,
                superPoder,
                localizacao,
                pato,
                pais.equals(LocalizacaoUtils.EUA)
        );
    }

    public Pato editarPato(PatoRequest patoRequest, Drone drone, SuperPoder superPoder, Pais pais, Localizacao localizacao, Pato pato) {
        Pais paisAntigo = pato.getLocalizacao().getCidade().getEstado().getPais();
        return definirPato(
                patoRequest,
                drone,
                superPoder,
                localizacao,
                pato,
                pais.equals(LocalizacaoUtils.EUA) && !paisAntigo.equals(LocalizacaoUtils.EUA)
        );
    }

    public Pato definirPato(PatoRequest patoRequest, Drone drone, SuperPoder superPoder, Localizacao localizacao, Pato pato, boolean deveConverter) {
        pato.setDroneQueEncontrou(drone);

        //realiza as conversões quando necessário
        pato.setAltura(deveConverter ? UnidadesUtils.peParaCentimetro(patoRequest.altura()) : patoRequest.altura());
        pato.setPeso(deveConverter ? UnidadesUtils.libraParaGrama(patoRequest.peso()) : patoRequest.peso());

        pato.setPrecisaoDoGpsQuandoEncontrado(patoRequest.precisao());
        pato.setLocalizacao(localizacao);

        pato.setQuantidadeMutacoes(patoRequest.quantidadeMutacoes());
        pato.setEstadoHibernacao(patoRequest.estadoHibernacao());

        if (patoRequest.estadoHibernacao().equals(EstadoHibernacao.DESPERTO)) {
            pato.setSuperPoder(superPoder);
            pato.setBpm(null);
        } else {
            pato.setBpm(patoRequest.bpm());
            pato.setSuperPoder(null);
        }

        return pato;
    }
}
