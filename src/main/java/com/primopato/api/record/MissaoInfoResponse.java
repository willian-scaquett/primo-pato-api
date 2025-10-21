package com.primopato.api.record;

import java.math.BigDecimal;

public record MissaoInfoResponse(
        long idPato,
        String defesaRecomendada,
        double distancia,
        double gastoCombustivelIda,
        double gastoCombustivelVolta,
        double gastoCombustivelTotal,
        float risco,
        float ganhoCientifico,
        float ganhoParanormal,
        String armaRecomendada,
        String abordagemRecomendada,
        String tamanhoRedeNecessaria,
        BigDecimal custo
) {}
