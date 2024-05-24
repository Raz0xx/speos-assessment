package com.devaleriola.speos_assessment.entities.partner;

import com.devaleriola.speos_assessment.entities.GenericDtoImpl;
import com.devaleriola.speos_assessment.utils.LocaleCustomSerializer;
import com.devaleriola.speos_assessment.utils.StringToLocaleCustomDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Objects;

public class Partner extends GenericDtoImpl implements PartnerBiz {

    @Schema(type = "string", example = "Bells & Whistles", description = "The name of the partner")
    private String name;
    @Schema(type = "string", example = "xxxxxxx", description = "The unique reference of the partner")
    private String reference;
    @JsonSerialize(using = LocaleCustomSerializer.class)
    @JsonDeserialize(using = StringToLocaleCustomDeserializer.class)
    @Schema(type = "string", example = "en_US", description = "A valid Locale of the partner")
    private Locale locale;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssxxx")
    @Schema(type = "string", example = "2017-10-03T12:18:46+00:00", description = "The ISO-8601 UTC date time when the partner is going to expire")
    private OffsetDateTime expirationTime;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getReference() {
        return reference;
    }

    @Override
    public void setReference(String reference) {
        this.reference = reference;
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    @Override
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Override
    public OffsetDateTime getExpirationTime() {
        return expirationTime;
    }

    @Override
    public void setExpirationTime(OffsetDateTime expirationTime) {
        this.expirationTime = expirationTime;
    }

    @Override
    public boolean hasValidLocale() {
        //the locale is set by the custom deserializer
        if (locale == null) {
            return true;
        }

        try {
            locale.getISO3Language();
            locale.getISO3Country();
        } catch (MissingResourceException exception) {
            return false;
        }

        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Partner partner = (Partner) o;
        return Objects.equals(name, partner.name) && Objects.equals(reference, partner.reference) && Objects.equals(locale, partner.locale) && Objects.equals(expirationTime, partner.expirationTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, reference, locale, expirationTime);
    }
}
