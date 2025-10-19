package com.primopato.api.enumerated;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EstadoHibernacao {
    DESPERTO("Desperto"),
    EM_TRANSE("Em Transe"),
    HIBERNACAO_PROFUNDA("Hibernação profunda");

    private final String nome;
}
