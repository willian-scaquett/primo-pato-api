package com.primopato.api.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UnidadesUtils {
    private final float FATOR_CONVERSAO_LIBRA = 453.59237f;
    private final float FATOR_CONVERSAO_PE = 30.48f;

    public static Float peParaCentimetro(Float medida) {
        // 1 pé = 30,48 centímetros
        return medida * FATOR_CONVERSAO_PE;
    }

    public static Float centimetroParaPe(Float medida) {
        // 1 pé = 30,48 centímetros
        return medida / FATOR_CONVERSAO_PE;
    }

    public static Float libraParaGrama(Float medida) {
        // 1 libra = 453.59237 gramas
        return medida * FATOR_CONVERSAO_LIBRA;
    }

    public static Float gramaParaLibra(Float medida) {
        // 1 libra = 453.59237 gramas
        return medida / FATOR_CONVERSAO_LIBRA;
    }

}
