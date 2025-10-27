package com.primopato.api.enumerated;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EstadoHibernacao {
    //Dorme como um bebê
    DESPERTO("Desperto", 2f, 25f),
    //Sabe aquele cochilo depois do almoço? É nesse estado de ação que o pato se encontra
    EM_TRANSE("Em Transe", 1.5f, 10f),
    //Acordado e pronto para destruir nossos drones
    HIBERNACAO_PROFUNDA("Hibernação profunda", 1f, 5f);

    private final String nome;
    private final Float potencializador;
    private final Float risco;
}
