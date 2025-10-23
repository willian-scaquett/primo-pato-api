package com.primopato.api.entity;

import com.primopato.api.enumerated.Abordagem;
import com.primopato.api.enumerated.ArmaDrone;
import com.primopato.api.enumerated.DefesaDrone;
import com.primopato.api.enumerated.TamanhoRede;
import com.primopato.api.utils.LocalizacaoUtils;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
public class MissaoInfo {

    private static final BigDecimal PRECO_COMBUSTIVEL = new BigDecimal("12.12");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "pato_id", nullable = false, unique = true)
    private Pato pato;

    @Column
    private DefesaDrone defesaDrone;

    @Column
    private Float desempenhoCombustivelPorLitroPosCaputura;

    @Column
    private Float risco;

    @Column
    private Float ganhoCientifico;

    @Column
    private Float ganhoParanormal;

    @Column
    private ArmaDrone armaDrone;

    @Column
    private Abordagem abordagem;

    @Column
    private TamanhoRede tamanhoRede;

    public void setRisco(Float risco) {
        this.risco = Math.min(risco, 100);
    }

    public void setGanhoCientifico(Float ganhoCientifico) {
        this.ganhoCientifico = Math.min(ganhoCientifico, 100);
    }

    public void setGanhoParanormal(Float ganhoParanormal) {
        this.ganhoParanormal = Math.min(ganhoParanormal, 100);
    }

    public BigDecimal getCusto(Double quantidadeCombustivel) {
        return defesaDrone.getPreco()
                .add(PRECO_COMBUSTIVEL.multiply(BigDecimal.valueOf(quantidadeCombustivel)))
                .add(armaDrone.getPreco())
                .add(tamanhoRede.getPreco());
    }

    public Double getDistancia() {
        Coordenadas coordenadas = this.pato.getLocalizacao().getCoordenadas();
        return LocalizacaoUtils.distanciaKmEntreDsinECoordenadas(coordenadas.getLatitude(), coordenadas.getLongitude());
    }

    public Double getGastoCombustivelIda() {
        return getDistancia() / LocalizacaoUtils.COMBUSTIVEL_KM_L;
    }

    public Double getGastoCombustivelVolta() {
        return getDistancia() / this.desempenhoCombustivelPorLitroPosCaputura;
    }
}
