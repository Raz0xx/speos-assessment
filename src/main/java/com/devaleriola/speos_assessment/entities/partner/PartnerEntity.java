package com.devaleriola.speos_assessment.entities.partner;

import com.devaleriola.speos_assessment.entities.GenericEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.OffsetDateTime;
import java.util.Locale;

@Entity
@Table(name = "partners")
public class PartnerEntity extends GenericEntity {

    @Column(name = "companyName")
    private String name;

    @Column(name = "ref", unique = true)
    private String reference;

    @Column(name = "locale")
    private Locale locale;

    @Column(name = "expires")
    private OffsetDateTime expirationTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public OffsetDateTime getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(OffsetDateTime expirationTime) {
        this.expirationTime = expirationTime;
    }
}
