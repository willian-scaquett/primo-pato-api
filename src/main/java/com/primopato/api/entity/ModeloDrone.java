package com.primopato.api.entity;

import jakarta.persistence.*;

@Entity
public class ModeloDrone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column
    private String nome;

    @ManyToOne(targetEntity = FabricanteDrone.class, fetch = FetchType.LAZY)
    private FabricanteDrone fabricante;

}
