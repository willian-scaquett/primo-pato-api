package com.primopato.api.entity;

import com.primopato.api.enumerated.EstadoHibernacao;
import com.primopato.api.enumerated.TipoSuperPoder;
import jakarta.persistence.*;

@Entity
public class SuperPoder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column
    String nome;

    @Column
    @Enumerated(EnumType.STRING)
    private TipoSuperPoder tipo;
}
