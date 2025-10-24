package com.primopato.api.record;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.primopato.api.entity.MissaoInfo;
import com.primopato.api.utils.Float2CasasSerializer;
import com.primopato.api.utils.LocalizacaoUtils;

import java.math.BigDecimal;

public record MissaoInfoResponse(
        long idPato,
        String defesaRecomendada,
        @JsonSerialize(using = Float2CasasSerializer.class)
        double distancia,
        @JsonSerialize(using = Float2CasasSerializer.class)
        double rendimentoCombustivelIda,
        @JsonSerialize(using = Float2CasasSerializer.class)
        double rendimentoCombustivelVolta,
        @JsonSerialize(using = Float2CasasSerializer.class)
        double gastoCombustivelIda,
        @JsonSerialize(using = Float2CasasSerializer.class)
        double gastoCombustivelVolta,
        @JsonSerialize(using = Float2CasasSerializer.class)
        double gastoCombustivelTotal,
        @JsonSerialize(using = Float2CasasSerializer.class)
        float risco,
        @JsonSerialize(using = Float2CasasSerializer.class)
        float ganhoCientifico,
        @JsonSerialize(using = Float2CasasSerializer.class)
        float ganhoParanormal,
        String armaRecomendada,
        String abordagemRecomendada,
        String tamanhoRedeNecessaria,
        @JsonSerialize(using = Float2CasasSerializer.class)
        BigDecimal custo
) {
    public MissaoInfoResponse(MissaoInfo missaoInfo) {
        this(
                missaoInfo.getPato().getId(),
                missaoInfo.getDefesaDrone().getNome(),
                missaoInfo.getDistancia(),
                missaoInfo.getDesempenhoCombustivelPorLitroPosCaputura(),
                LocalizacaoUtils.COMBUSTIVEL_KM_L,
                missaoInfo.getGastoCombustivelIda(),
                missaoInfo.getGastoCombustivelIda() + missaoInfo.getGastoCombustivelVolta(),
                missaoInfo.getGastoCombustivelVolta(),
                missaoInfo.getRisco(),
                missaoInfo.getGanhoCientifico(),
                missaoInfo.getGanhoParanormal(),
                missaoInfo.getArmaDrone().getNome(),
                missaoInfo.getAbordagem().getNome(),
                missaoInfo.getTamanhoRede().getNome(),
                missaoInfo.getCusto(missaoInfo.getGastoCombustivelIda() + missaoInfo.getGastoCombustivelVolta())
        );
    }
}