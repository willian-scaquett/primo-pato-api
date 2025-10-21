package com.primopato.api.service.assembler;

import com.primopato.api.entity.*;
import com.primopato.api.enumerated.*;
import com.primopato.api.repository.MissaoInfoRepository;
import com.primopato.api.service.PatoService;
import com.primopato.api.utils.LocalizacaoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MissaoInfoAssembler {

    private final static Integer BPM_BASE_ABORDAGEM = 200;
    private final static Float BPM_BASE_RISCO = 130f;
    //A cada 10 gramas do pago, o combustível rende 1km/l a menos
    private final static Float PESO_BASE_CALCULO_COMBUSTIVEL = 10f;

    private final MissaoInfoRepository missaoInfoRepository;
    private final PatoService patoService;

    public MissaoInfo obterOuCriarMissaoInfo(Long idPato) {
        Pato pato = patoService.getPato(idPato);
        return missaoInfoRepository.findByPato(pato).orElseGet(() -> criarMissaoInfo(pato));
    }

    private MissaoInfo criarMissaoInfo(Pato pato) {
        MissaoInfo missaoInfo = new MissaoInfo();

        missaoInfo.setPato(pato);

        SuperPoder superPoder = pato.getSuperPoder();
        missaoInfo.setDefesaDrone(superPoder != null ? superPoder.getTipo().getDefesa() : DefesaDrone.NENHUMA);

        missaoInfo.setDesempenhoCombustivelPorLitroPosCaputura(LocalizacaoUtils.COMBUSTIVEL_KM_L - (pato.getPeso() / PESO_BASE_CALCULO_COMBUSTIVEL));
        //missaoInfo.setRisco()

        Float potencializador = pato.getEstadoHibernacao().getPotencializador();
        missaoInfo.setGanhoCientifico(pato.getQuantidadeMutacoes() * potencializador);
        missaoInfo.setGanhoParanormal(superPoder != null ? superPoder.getTipo().getGanhoParanormalBase() * potencializador : 0);

        missaoInfo.setArmaDrone(escolherArma(pato));
        missaoInfo.setAbordagem(escolherAbordagem(pato));
        missaoInfo.setTamanhoRede(TamanhoRede.porAlturaPato(pato.getAltura()));
        missaoInfo.setRisco(calculaRisco(pato));

        return missaoInfoRepository.save(missaoInfo);
    }

    private ArmaDrone escolherArma(Pato pato) {
        if (pato.getEstadoHibernacao().equals(EstadoHibernacao.HIBERNACAO_PROFUNDA)) {
            return ArmaDrone.CAPSULA_CONGELAMENTO;
        }

        if (pato.getSuperPoder() != null) {
            TipoSuperPoder tipoSuperPoder = pato.getSuperPoder().getTipo();

            if (tipoSuperPoder.equals(TipoSuperPoder.SOBRENATURAL)) {
                return ArmaDrone.AGUA_BENTA;
            }

            if (tipoSuperPoder.equals(TipoSuperPoder.TELETRANSPORTE) || tipoSuperPoder.equals(TipoSuperPoder.VELOCIDADE)) {
                return ArmaDrone.ONDA_CHOQUE;
            }

            return ArmaDrone.MISSIL_TELEGUIADO;
        }

        return ArmaDrone.RAIO_LASER;
    }

    private Abordagem escolherAbordagem(Pato pato) {
        if (pato.getEstadoHibernacao().equals(EstadoHibernacao.HIBERNACAO_PROFUNDA) || pato.getEstadoHibernacao().equals(EstadoHibernacao.EM_TRANSE) ) {
            if (pato.getBpm() > BPM_BASE_ABORDAGEM) { //A frequência cardíaca de um pato em repouso varia geralmente entre 130 e 230 batimentos por minuto (bpm)
                return Abordagem.FURTIVO;
            }

            return Abordagem.COMEDIDO;
        }

        return Abordagem.COMBATIVO;
    }

    private Float calculaRisco(Pato pato) {
        Float riscoSuperPoder = pato.getSuperPoder() != null ? pato.getSuperPoder().getTipo().getRisco() : 0f;
        Float riscoHibernacao = pato.getEstadoHibernacao().getRisco();
        float potencializadorBpm = pato.getBpm() != null && pato.getBpm() > 0f
                ? (Float.valueOf(pato.getBpm()) % BPM_BASE_RISCO) / 100f
                : 1f;

        return (riscoSuperPoder + riscoHibernacao) * potencializadorBpm;
    }
}
