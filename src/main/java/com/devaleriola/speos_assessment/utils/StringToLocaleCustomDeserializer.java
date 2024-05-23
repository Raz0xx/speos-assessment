package com.devaleriola.speos_assessment.utils;

import com.devaleriola.speos_assessment.exceptions.InvalidLocaleException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.IllformedLocaleException;
import java.util.Locale;

public class StringToLocaleCustomDeserializer extends JsonDeserializer<Locale> {

    @Override
    public Locale deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        String localeString = jsonParser.getText();

        if (localeString == null || localeString.equals("")) {
            return null;
        }

        try {
            Locale parsedLocale = new Locale.Builder().setLanguageTag(localeString.replace('_', '-')).build();
            if (parsedLocale == null) {
                throw new InvalidLocaleException(localeString);
            }

            return parsedLocale;
        } catch (IllformedLocaleException exception) {
            throw new InvalidLocaleException(localeString);
        }
    }
}
