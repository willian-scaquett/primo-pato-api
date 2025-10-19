package com.primopato.api.entity;

import jakarta.persistence.*;

@Entity
public class Cidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column
    String nome;

    @ManyToOne(targetEntity = Estado.class, fetch = FetchType.LAZY)
    private Estado estado;
}
