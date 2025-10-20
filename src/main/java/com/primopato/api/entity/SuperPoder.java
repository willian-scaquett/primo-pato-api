package com.primopato.api.entity;

import com.primopato.api.enumerated.TipoSuperPoder;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class SuperPoder {

    public SuperPoder(String nome, TipoSuperPoder tipo) {
        this.nome = nome;
        this.tipo = tipo;
    }

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
