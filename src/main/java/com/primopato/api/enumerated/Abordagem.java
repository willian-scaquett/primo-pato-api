package com.primopato.api.enumerated;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Abordagem {
    FURTIVO("Furtivo"), //Cancelador de ruídos no modo turbo.
    COMEDIDO("Comedido"), //Cancelador de ruídos ligado.
    COMBATIVO("Combativo"); //Cancelador de ruídos desligado. Toda energia no combate.

    private final String nome;
}
