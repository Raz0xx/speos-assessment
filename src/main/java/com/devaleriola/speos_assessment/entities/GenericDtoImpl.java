package com.devaleriola.speos_assessment.entities;

public abstract class GenericDtoImpl implements GenericDto {

    protected long id;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

}
