package com.primopato.api.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Cidade {

    public Cidade(String nome, Estado estado) {
        this.nome = nome;
        this.estado = estado;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column
    String nome;

    @ManyToOne(targetEntity = Estado.class, fetch = FetchType.LAZY)
    private Estado estado;
}
