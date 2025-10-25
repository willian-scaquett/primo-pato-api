package com.primopato.api.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UnidadesUtils {

    public static Float peParaCentimetro(Float medida) {
        // 1 pé = 30,48 centímetros
        return medida * 30.48f;
    }

    public static Float centimetroParaPe(Float medida) {
        // 1 pé = 30,48 centímetros
        return medida / 30.48f;
    }

    public static Float libraParaGrama(Float medida) {
        // 1 libra = 453.59237 gramas
        return medida * 453.59237f;
    }

    public static Float gramaParaLibra(Float medida) {
        // 1 libra = 453.59237 gramas
        return medida / 453.59237f;
    }

}
