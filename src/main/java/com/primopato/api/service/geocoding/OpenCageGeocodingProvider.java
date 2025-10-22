package com.primopato.api.service.geocoding;

import com.opencagedata.jopencage.JOpenCageGeocoder;
import com.opencagedata.jopencage.model.JOpenCageResult;
import com.opencagedata.jopencage.model.JOpenCageReverseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OpenCageGeocodingProvider implements GeocodingProvider {

    private static final String API_KEY = "bda0a070c2b34c8f894ab21fb5f39022";
    private static final String LANGUAGE = "pt-BR";

    @Override
    public GeocodingResult reverse(Double latitude, Double longitude) {
        JOpenCageGeocoder geocoder = new JOpenCageGeocoder(API_KEY);
        JOpenCageReverseRequest request = new JOpenCageReverseRequest(latitude, longitude);
        request.setLanguage(LANGUAGE);
        request.setLimit(1);

        JOpenCageResult result = geocoder.reverse(request).getResults().getFirst();

        String cidade = result.getComponents().getCity();

        return new GeocodingResult(
                result.getFormatted(),
                cidade != null ? cidade : result.getComponents().getTown(),
                result.getComponents().getState(),
                result.getComponents().getCountry()
        );
    }
}
