package com.primopato.api.entity;

import com.primopato.api.enumerated.Abordagem;
import com.primopato.api.enumerated.ArmaDrone;
import com.primopato.api.enumerated.DefesaDrone;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class MissaoInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @OneToOne(targetEntity = Pato.class, fetch = FetchType.LAZY)
    private Pato pato;

    @Column
    private DefesaDrone defesaDrone;

    @Column
    private Float desempenhoCombustivelPorLitro;

    @Column
    private Float custoOperacional;

    @Column
    private Integer risco;

    @Column
    private Integer ganhoCientifico;

    @Column
    private Integer ganhoParanormal;

    @Column
    private ArmaDrone armaDrone;

    @Column
    private Abordagem abordagem;

}
