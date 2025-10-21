package com.primopato.api.entity;

import com.primopato.api.enumerated.EstadoHibernacao;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Pato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private Float altura; //em centímetros

    @Column(nullable = false)
    private Float peso; //em gramas

    @ManyToOne(targetEntity = Localizacao.class, fetch = FetchType.LAZY)
    private Localizacao localizacao;

    @ManyToOne(targetEntity = Drone.class, fetch = FetchType.LAZY)
    private Drone droneQueEncontrou;

    @Column(nullable = false)
    private Float precisaoDoGpsQuandoEncontrado; //em centímetros

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoHibernacao estadoHibernacao;

    @Column
    private Integer bpm;

    @Column(nullable = false)
    private Integer quantidadeMutacoes;

    @ManyToOne(targetEntity = SuperPoder.class, fetch = FetchType.LAZY)
    private SuperPoder superPoder;

    @OneToOne(targetEntity = MissaoInfo.class, fetch = FetchType.LAZY)
    private MissaoInfo missaoInfo;

    @Column
    private boolean capturado = false;
}
