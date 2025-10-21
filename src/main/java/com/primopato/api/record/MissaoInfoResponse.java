package com.primopato.api.record;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;

public record MissaoInfoResponse(
        long idPato,
        String defesaRecomendada,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
        double distancia,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
        double gastoCombustivelIda,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
        double gastoCombustivelVolta,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
        double gastoCombustivelTotal,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
        float risco,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
        float ganhoCientifico,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
        float ganhoParanormal,
        String armaRecomendada,
        String abordagemRecomendada,
        String tamanhoRedeNecessaria,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
        BigDecimal custo
) {}