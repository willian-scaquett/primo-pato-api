package com.primopato.api.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Objects;

@Data
@Entity
public class Localizacao {

    @EmbeddedId
    private Coordenadas coordenadas;

    @Column
    private String endereco;

    @Column
    private String pontoReferencia;

    @ManyToOne(targetEntity = Cidade.class, fetch = FetchType.LAZY)
    private Cidade cidade;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Localizacao that = (Localizacao) o;
        return Objects.equals(coordenadas, that.coordenadas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordenadas);
    }
}
