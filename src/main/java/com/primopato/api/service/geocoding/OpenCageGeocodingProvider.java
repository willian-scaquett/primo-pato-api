package com.primopato.api.service.geocoding;

import com.opencagedata.jopencage.JOpenCageGeocoder;
import com.opencagedata.jopencage.model.JOpenCageResult;
import com.opencagedata.jopencage.model.JOpenCageReverseRequest;
import com.primopato.api.utils.CustomStringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OpenCageGeocodingProvider implements GeocodingProvider {

    private static final String API_KEY = "bda0a070c2b34c8f894ab21fb5f39022";
    private static final String LANGUAGE = "pt-BR";
    private static final String LOCAL_SEM_NOME = "-";

    @Override
    public GeocodingResult reverse(Double latitude, Double longitude) {
        JOpenCageGeocoder geocoder = new JOpenCageGeocoder(API_KEY);
        JOpenCageReverseRequest request = new JOpenCageReverseRequest(latitude, longitude);
        request.setLanguage(LANGUAGE);
        request.setLimit(1);

        JOpenCageResult result = geocoder.reverse(request).getResults().getFirst();

        return new GeocodingResult(
                result.getFormatted(),
                CustomStringUtils.coalesce(result.getComponents().getCity(), result.getComponents().getTown(), result.getComponents().getVillage(), result.getComponents().getCityDistrict(), LOCAL_SEM_NOME),
                CustomStringUtils.coalesce(result.getComponents().getState(), LOCAL_SEM_NOME),
                CustomStringUtils.coalesce(result.getComponents().getCountry(), LOCAL_SEM_NOME)
        );
    }
}
