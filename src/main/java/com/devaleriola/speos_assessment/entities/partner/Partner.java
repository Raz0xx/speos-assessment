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
}
