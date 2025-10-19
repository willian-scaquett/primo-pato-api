package com.primopato.api.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Localizacao {

    @Id
    @Column(nullable = false)
    private Double latitude;

    @Id
    @Column(nullable = false)
    private Double longitude;

    @Column
    private String enderecoSuperPoder;

    @Column
    private String pontoReferencia;

    @ManyToOne(targetEntity = Cidade.class, fetch = FetchType.LAZY)
    private Cidade cidade;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Localizacao that = (Localizacao) o;
        return Objects.equals(latitude, that.latitude) && Objects.equals(longitude, that.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }
}
