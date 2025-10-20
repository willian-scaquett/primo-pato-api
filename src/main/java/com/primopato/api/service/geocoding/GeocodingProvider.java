package com.primopato.api.service.geocoding;

public interface GeocodingProvider {
    GeocodingResult reverse(Double latitude, Double longitude);
}