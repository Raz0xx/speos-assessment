package com.devaleriola.speos_assessment.entities.partner;

import com.devaleriola.speos_assessment.entities.GenericDto;

import java.time.OffsetDateTime;
import java.util.Locale;

public interface PartnerDto extends GenericDto {

    String getName();

    void setName(String name);

    String getReference();

    void setReference(String reference);

    Locale getLocale();

    void setLocale(Locale locale);

    OffsetDateTime getExpirationTime();

    void setExpirationTime(OffsetDateTime expirationTime);

}
