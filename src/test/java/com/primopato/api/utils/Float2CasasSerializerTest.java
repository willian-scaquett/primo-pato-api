package com.primopato.api.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

class Float2CasasSerializerTest {

    @Test
    void testSerialize_ComValor() throws IOException {
        Float2CasasSerializer serializer = new Float2CasasSerializer();
        JsonGenerator gen = mock(JsonGenerator.class);
        SerializerProvider provider = mock(SerializerProvider.class);

        serializer.serialize(12.3456f, gen, provider);

        verify(gen).writeNumber("12.35");
    }

    @Test
    void testSerialize_ValorNulo() throws IOException {
        Float2CasasSerializer serializer = new Float2CasasSerializer();
        JsonGenerator gen = mock(JsonGenerator.class);
        SerializerProvider provider = mock(SerializerProvider.class);

        serializer.serialize(null, gen, provider);

        verify(gen).writeNull();
    }

    @Test
    void testSerialize_ComInteiro() throws IOException {
        Float2CasasSerializer serializer = new Float2CasasSerializer();
        JsonGenerator gen = mock(JsonGenerator.class);
        SerializerProvider provider = mock(SerializerProvider.class);

        serializer.serialize(10, gen, provider);

        verify(gen).writeNumber("10.00");
    }
}
