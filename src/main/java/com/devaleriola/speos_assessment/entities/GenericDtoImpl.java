package com.devaleriola.speos_assessment.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public abstract class GenericDtoImpl implements GenericDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(type = "long", example = "1", description = "The database id")
    private long id;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

}
