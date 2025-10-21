package com.primopato.api.enumerated;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ArmaDrone {
    AGUA("Água"),
    FOGO("Fogo"),
    ELETRICIDADE("Eletricidade"),
    CALOR("Calor"),
    VELOCIDADE("Velocidade"),
    TELETRANSPORTE("Teletransporte"),
    PSIQUICO("Psíquico"),
    SOBRENATURAL("Sobrenatural"),
    OUTRO("Outro");

    private final String nome;
}
