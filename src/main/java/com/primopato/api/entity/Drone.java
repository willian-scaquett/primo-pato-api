package com.primopato.api.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Drone {

    public Drone(String numeroSerie, ModeloDrone modelo) {
        this.numeroSerie = numeroSerie;
        this.modelo = modelo;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String numeroSerie;

    @ManyToOne(targetEntity = ModeloDrone.class, fetch = FetchType.LAZY)
    private ModeloDrone modelo;
}
