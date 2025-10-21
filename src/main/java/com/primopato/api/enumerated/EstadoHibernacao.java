package com.primopato.api.enumerated;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EstadoHibernacao {
    DESPERTO("Desperto", 2f, 25f),
    EM_TRANSE("Em Transe", 1.5f, 10f),
    HIBERNACAO_PROFUNDA("Hibernação profunda", 1f, 5f);

    private final String nome;
    private final Float potencializador;
    private final Float risco;
}
