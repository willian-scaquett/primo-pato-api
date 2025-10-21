package com.primopato.api.enumerated;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Abordagem {
    FURTIVO("Furtivo"),
    COMEDIDO("Comedido"),
    COMBATIVO("Combativo");

    private final String nome;
}
