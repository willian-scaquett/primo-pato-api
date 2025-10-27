package com.primopato.api.service.assembler;

import com.primopato.api.entity.*;
import com.primopato.api.enumerated.*;
import com.primopato.api.utils.LocalizacaoUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class MissaoInfoAssembler {

    private static final Integer BPM_BASE_ABORDAGEM = 200;
    private static final Float BPM_BASE_RISCO = 130f;
    private static final Float PESO_BASE_CALCULO_COMBUSTIVEL = 1000f;

    public MissaoInfo montarMissaoInfo(Pato pato, MissaoInfo missaoInfo) {
        if (missaoInfo == null) {
            missaoInfo = new MissaoInfo();
            missaoInfo.setPato(pato);
        }

        SuperPoder superPoder = pato.getSuperPoder();
        //Cada tipo de super-poder conhecido tem a sua defesa. Se o pato não tem super-poder registrado
        //quer dizer que ele não atacou o primeiro drone. Logo, se não acordarmos o pato da sua hibernação
        // (e para isso que serve o cancelador de ruídos do drone), não será necessário uma defesa para a
        // missão de captura.
        missaoInfo.setDefesaDrone(superPoder != null ? superPoder.getTipo().getDefesa() : DefesaDrone.NENHUMA);

        //A cada 1000 gramas (1kg) do pato, o combustível do drone rende 1km/l a menos.
        missaoInfo.setDesempenhoCombustivelPorLitroPosCaputura(LocalizacaoUtils.COMBUSTIVEL_KM_L - (pato.getPeso() / PESO_BASE_CALCULO_COMBUSTIVEL));

        //Quando o pato está desperto ou em transe, há mais ganhos potenciais do que quando ele
        //está em hibernação profunda.
        Float potencializador = pato.getEstadoHibernacao().getPotencializador();
        //Quanto mais mutações, mais sequencialmente de DNA temos, logo, mais ganho científico.
        missaoInfo.setGanhoCientifico(pato.getQuantidadeMutacoes() * potencializador);
        //Um pato com super-poderes? Alguns podem até possuir um fator biológico envolvido.
        //Já outros servirão de base para as pesquisas sobre fenômenos paranormais.
        missaoInfo.setGanhoParanormal(superPoder != null ? superPoder.getTipo().getGanhoParanormalBase() * potencializador : 0);

        missaoInfo.setArmaDrone(escolherArma(pato));
        missaoInfo.setAbordagem(escolherAbordagem(pato));
        missaoInfo.setTamanhoRede(TamanhoRede.porAlturaPato(pato.getAltura()));
        missaoInfo.setRisco(calculaRisco(pato));

        return missaoInfo;
    }

    private ArmaDrone escolherArma(Pato pato) {
        if (pato.getEstadoHibernacao().equals(EstadoHibernacao.HIBERNACAO_PROFUNDA)) {
            //Ele já está hibernando profundamente. Não precisaremos de velocidade para atacá-lo,
            //tampouco poder de fogo. Basta congelá-lo.
            return ArmaDrone.CAPSULA_CONGELAMENTO;
        }

        if (pato.getSuperPoder() != null) {
            TipoSuperPoder tipoSuperPoder = pato.getSuperPoder().getTipo();

            if (tipoSuperPoder.equals(TipoSuperPoder.SOBRENATURAL)) {
                //Se funciona com vampiros, funcionará também com um pato assombração.
                return ArmaDrone.AGUA_BENTA;
            }

            if (tipoSuperPoder.equals(TipoSuperPoder.TELETRANSPORTE) || tipoSuperPoder.equals(TipoSuperPoder.VELOCIDADE)) {
                //Deslocamento é sua maior virtude. Como lidar com isso? Atingindo a maior
                //área possível com uma onda de choque
                return ArmaDrone.ONDA_CHOQUE;
            }

            //Acertá-lo não será difícl. Porém, por ter super-poderes, melhor garantir um bom poder de fogo.
            return ArmaDrone.MISSIL_TELEGUIADO;
        }

        //A arma padrão do drone deve bastar para pato em transe.
        return ArmaDrone.RAIO_LASER;
    }

    private Abordagem escolherAbordagem(Pato pato) {
        if (pato.getEstadoHibernacao().equals(EstadoHibernacao.HIBERNACAO_PROFUNDA) || pato.getEstadoHibernacao().equals(EstadoHibernacao.EM_TRANSE) ) {
            if (pato.getBpm() > BPM_BASE_ABORDAGEM) { //A frequência cardíaca de um pato em repouso varia geralmente entre 130 e 230 batimentos por minuto (bpm)
                return Abordagem.FURTIVO; //Há grandes chances do pato acordar. Não façamos barulho algum.
            }

            return Abordagem.COMEDIDO; //Ele hiberna, porém o seu coração bate lentamente. Ruídos leves são toleráveis
        }

        return Abordagem.COMBATIVO; //O pato está desperto. Precisamos dedicar toda a energia do drone para o combate
    }

    private Float calculaRisco(Pato pato) {
        Float riscoSuperPoder = pato.getSuperPoder() != null ? pato.getSuperPoder().getTipo().getRisco() : 0f;
        Float riscoHibernacao = pato.getEstadoHibernacao().getRisco();
        float potencializadorBpm = pato.getBpm() != null && pato.getBpm() > 0f
                ? (Float.valueOf(pato.getBpm()) % BPM_BASE_RISCO) / 100f
                : 1f;

        //O super-poder e o tipo de hibernação são o que definem o risco.
        //O quão ativo está o seu organismo potencializa esses valor.
        return (riscoSuperPoder + riscoHibernacao) * potencializadorBpm;
    }
}
