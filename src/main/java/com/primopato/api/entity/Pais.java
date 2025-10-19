package com.primopato.api.entity;

import jakarta.persistence.*;

@Entity
public class Pais {

    public Pais(String nome) {
        this.nome = nome;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column
    String nome;
}
