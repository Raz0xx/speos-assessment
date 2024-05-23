package com.devaleriola.speos_assessment.entities.test;

import com.devaleriola.speos_assessment.entities.GenericEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class TestEntity extends GenericEntity {

    @Column(name = "text")
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
