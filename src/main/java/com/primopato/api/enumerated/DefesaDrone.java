package com.primopato.api.enumerated;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public enum DefesaDrone {
    //Cada defesa e seu respectivo valor em patocoins
    REVESTIMENTO_HIDROFOBICO("Revestimento Hidrofóbico", new BigDecimal(500)),
    CERAMICA_REFRATARIA("Cerâmica Refratária", new BigDecimal(800)),
    ISOLAMENTO_GRAFENO("Isolamento de Grafeno", new BigDecimal(1000)),
    CAMPO_TERMORREGULADOR_ADAPTATIVO("Campo Termorregulador Adaptativo", new BigDecimal(1200)),
    RADAR_INERCIAL_PREVISIVO("Radar Inercial Previsivo", new BigDecimal(2000)),
    SENSOR_QUANTICO_FENDAS("Sensor Quântico de Fendas", new BigDecimal(5000)),
    ALHO("Alho", new BigDecimal(2)),
    ESCUDO_ADAPTATIVO_IA("Escudo adaptativo com IA", new BigDecimal(20000)),
    NENHUMA("Nenhuma", new BigDecimal(0));

    private final String nome;
    private final BigDecimal preco;
}
