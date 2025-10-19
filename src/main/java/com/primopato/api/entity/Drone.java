package com.primopato.api.entity;

import jakarta.persistence.*;

@Entity
public class Drone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String numeroSerie;

    @ManyToOne(targetEntity = ModeloDrone.class, fetch = FetchType.LAZY)
    private ModeloDrone modelo;
}
