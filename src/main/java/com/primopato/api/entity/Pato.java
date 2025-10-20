package com.primopato.api.entity;

import com.primopato.api.enumerated.EstadoHibernacao;
import com.primopato.api.utils.LocalizacaoUtils;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Pato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column
    private Float altura; //em centímetros

    @Column
    private Float peso; //em gramas

    @ManyToOne(targetEntity = Localizacao.class, fetch = FetchType.LAZY)
    private Localizacao localizacao;

    @ManyToOne(targetEntity = Drone.class, fetch = FetchType.LAZY)
    private Drone droneQueEncontrou;

    @Column
    private Float precisaoDoGpsQuandoEncontrado; //em centímetros

    @Column
    @Enumerated(EnumType.STRING)
    private EstadoHibernacao estadoHibernacao;

    @Column
    private Integer bpm;

    @Column
    private Integer quantidadeMutacoes;

    @ManyToOne(targetEntity = SuperPoder.class, fetch = FetchType.LAZY)
    private SuperPoder superPoder;
}
