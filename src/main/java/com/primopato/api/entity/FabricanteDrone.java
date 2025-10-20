package com.primopato.api.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class FabricanteDrone {

    public FabricanteDrone(String nome, Pais pais) {
        this.nome = nome;
        this.pais = pais;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @ManyToOne(targetEntity = Pais.class, fetch = FetchType.LAZY)
    private Pais pais;

}
