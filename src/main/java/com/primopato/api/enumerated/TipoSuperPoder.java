package com.primopato.api.enumerated;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoSuperPoder {
    AGUA("Água", DefesaDrone.REVESTIMENTO_HIDROFOBICO, 0f, 20f),
    FOGO("Fogo", DefesaDrone.CERAMICA_REFRATARIA, 0f, 30f),
    ELETRICIDADE("Eletricidade", DefesaDrone.ISOLAMENTO_GRAFENO, 0f, 40f),
    CALOR("Calor", DefesaDrone.CAMPO_TERMORREGULADOR_ADAPTATIVO, 0f, 40f),
    VELOCIDADE("Velocidade", DefesaDrone.RADAR_INERCIAL_PREVISIVO, 0f, 50f),
    TELETRANSPORTE("Teletransporte", DefesaDrone.SENSOR_QUANTICO_FENDAS, 10f, 50f),
    PSIQUICO("Psíquico", DefesaDrone.NENHUMA, 30f, 0f),
    SOBRENATURAL("Sobrenatural", DefesaDrone.ALHO, 50f, 10f),
    OUTRO("Outro", DefesaDrone.ESCUDO_ADAPTATIVO_IA, 0f, 75f);

    private final String nome;
    private final DefesaDrone defesa;
    private final Float ganhoParanormalBase;
    private final Float risco;
}
