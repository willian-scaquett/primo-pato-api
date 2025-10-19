package com.primopato.api.entity;

import jakarta.persistence.*;

@Entity
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column
    String nome;

    @ManyToOne(targetEntity = Pais.class, fetch = FetchType.LAZY)
    private Pais pais;
}
