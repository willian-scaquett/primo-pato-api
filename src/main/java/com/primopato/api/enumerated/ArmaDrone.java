package com.primopato.api.enumerated;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public enum ArmaDrone {
    //Cada arma e seu respectivo valor em patocoins
    CAPSULA_CONGELAMENTO("Cápsula de Congelamento", new BigDecimal(350)),
    AGUA_BENTA("Água Benta", new BigDecimal(10)),
    RAIO_LASER("Raio Laser", new BigDecimal(400)),
    ONDA_CHOQUE("Onda de Choque", new BigDecimal(900)),
    MISSIL_TELEGUIADO("Míssil Teleguiado", new BigDecimal(750));

    private final String nome;
    private final BigDecimal preco;
}
