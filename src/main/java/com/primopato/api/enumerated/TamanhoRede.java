package com.primopato.api.enumerated;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public enum TamanhoRede {
    //Sem segredos. É como escolher uma roupa: precisa caber, mas não pode ser larga demais (ele poderia escapar
    //pelos buraquinhos).
    PEQUENA("Pequena", new BigDecimal(100), 0, 1000),
    MEDIA("Média", new BigDecimal(200), 1000, 2000),
    GRANDE("Grande", new BigDecimal(300), 2000, 5000),
    EXTRA_GRANDE("Extra Grande", new BigDecimal(500), 5000, 10000),
    GIGANTE("Gigante", new BigDecimal(1000), 10000, Float.MAX_VALUE);

    private final String nome;
    private final BigDecimal preco;
    private final float min;
    private final float max;

    public static TamanhoRede porAlturaPato(float alturaPato) {
        for (TamanhoRede tr : values()) {
            if (alturaPato >= tr.min && alturaPato < tr.max) {
                return tr;
            }
        }
        return PEQUENA;
    }
}
