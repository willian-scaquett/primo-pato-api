package com.primopato.api.utils;

import com.primopato.api.entity.Pais;

public class LocalizacaoUtils {

    public final static Pais EUA = new Pais("Estados Unidos");
    public final static Float COMBUSTIVEL_KM_L = 300f;
    public final static double LATITUDE_DSIN = -22.21389;
    public final static double LONGITUDE_DSIN = -49.94583;

    //Usado para calcular a distância entre o alvo e a base de operações
    public static double distanciaKmEntreDsinECoordenadas(double lat, double lon) {
        return LocalizacaoUtils.distanciaKmEntreCoordenadas(LATITUDE_DSIN, LONGITUDE_DSIN, lat, lon);
    }

    public static double distanciaKmEntreCoordenadas(double lat1, double lon1, double lat2, double lon2) {
        final double RAIO_TERRA_KM = 6371.0;

        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        double dlat = lat2Rad - lat1Rad;
        double dlon = lon2Rad - lon1Rad;

        // Fórmula de Haversine
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1Rad) * Math.cos(lat2Rad)
                * Math.pow(Math.sin(dlon / 2), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return RAIO_TERRA_KM * c;
    }
}
