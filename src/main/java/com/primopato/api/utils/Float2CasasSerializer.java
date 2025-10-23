package com.primopato.api.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.text.DecimalFormat;

public class Float2CasasSerializer extends JsonSerializer<Number> {
    private static final DecimalFormat df = new DecimalFormat("#0.00");

    @Override
    public void serialize(Number value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {
            gen.writeNumber(df.format(value));
        }
    }
}