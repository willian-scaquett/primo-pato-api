package com.primopato.api.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class ModeloDrone {

    public ModeloDrone(String nome, FabricanteDrone fabricante) {
        this.nome = nome;
        this.fabricante = fabricante;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @ManyToOne(targetEntity = FabricanteDrone.class, fetch = FetchType.LAZY)
    private FabricanteDrone fabricante;

}
