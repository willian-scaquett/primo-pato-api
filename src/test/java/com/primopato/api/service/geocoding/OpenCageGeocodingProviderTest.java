package com.primopato.api.service.geocoding;

import com.opencagedata.jopencage.JOpenCageGeocoder;
import com.opencagedata.jopencage.model.JOpenCageComponents;
import com.opencagedata.jopencage.model.JOpenCageResponse;
import com.opencagedata.jopencage.model.JOpenCageResult;
import com.opencagedata.jopencage.model.JOpenCageReverseRequest;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OpenCageGeocodingProviderTest {

    @Test
    void testReverse_Sucesso() {
        JOpenCageComponents components = mock(JOpenCageComponents.class);
        when(components.getCity()).thenReturn("Curitiba");
        when(components.getState()).thenReturn("Paraná");
        when(components.getCountry()).thenReturn("Brasil");

        JOpenCageResult result = mock(JOpenCageResult.class);
        when(result.getFormatted()).thenReturn("Curitiba, Paraná, Brasil");
        when(result.getComponents()).thenReturn(components);

        JOpenCageResponse response = mock(JOpenCageResponse.class);
        when(response.getResults()).thenReturn(List.of(result));

        try (MockedConstruction<JOpenCageGeocoder> mocked = mockConstruction(
                JOpenCageGeocoder.class,
                (mock, context) -> when(mock.reverse(any(JOpenCageReverseRequest.class))).thenReturn(response)
        )) {
            OpenCageGeocodingProvider provider = new OpenCageGeocodingProvider();
            GeocodingResult geo = provider.reverse(-25.43, -49.27);

            assertEquals("Curitiba, Paraná, Brasil", geo.endereco());
            assertEquals("Curitiba", geo.cidade());
            assertEquals("Paraná", geo.estado());
            assertEquals("Brasil", geo.pais());

            verify(mocked.constructed().getFirst()).reverse(any(JOpenCageReverseRequest.class));
        }
    }

    @Test
    void testReverse_ComFallbacks() {
        JOpenCageComponents components = mock(JOpenCageComponents.class);
        when(components.getCity()).thenReturn(null);
        when(components.getTown()).thenReturn(null);
        when(components.getVillage()).thenReturn(null);
        when(components.getCityDistrict()).thenReturn(null);
        when(components.getState()).thenReturn(null);
        when(components.getCountry()).thenReturn(null);

        JOpenCageResult result = mock(JOpenCageResult.class);
        when(result.getFormatted()).thenReturn(null);
        when(result.getComponents()).thenReturn(components);

        JOpenCageResponse response = mock(JOpenCageResponse.class);
        when(response.getResults()).thenReturn(List.of(result));

        try (MockedConstruction<JOpenCageGeocoder> mocked = mockConstruction(
                JOpenCageGeocoder.class,
                (mock, context) -> when(mock.reverse(any(JOpenCageReverseRequest.class))).thenReturn(response)
        )) {
            OpenCageGeocodingProvider provider = new OpenCageGeocodingProvider();
            GeocodingResult geo = provider.reverse(0.0, 0.0);

            assertEquals("-", geo.cidade());
            assertEquals("-", geo.estado());
            assertEquals("-", geo.pais());
        }
    }

    @Test
    void testReverse_SemResultados() {
        JOpenCageResponse response = mock(JOpenCageResponse.class);
        when(response.getResults()).thenReturn(Collections.emptyList());

        try (MockedConstruction<JOpenCageGeocoder> mocked = mockConstruction(
                JOpenCageGeocoder.class,
                (mock, context) -> when(mock.reverse(any(JOpenCageReverseRequest.class))).thenReturn(response)
        )) {
            OpenCageGeocodingProvider provider = new OpenCageGeocodingProvider();
            assertThrows(NoSuchElementException.class, () -> provider.reverse(10.0, 10.0));
        }
    }
}
