package com.devaleriola.speos_assessment.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Locale;

public class LocaleCustomSerializer extends JsonSerializer<Locale> {

    @Override
    public void serialize(Locale locale, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (locale == null) {
            return;
        }

        String localeString = locale.toString();
        jsonGenerator.writeString(localeString);
    }
}
