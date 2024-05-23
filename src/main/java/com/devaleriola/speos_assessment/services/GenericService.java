package com.devaleriola.speos_assessment.services;

import com.devaleriola.speos_assessment.entities.GenericDto;

import java.util.List;

public interface GenericService<T extends GenericDto> {

    T saveEntity(T object);

    T getEntityById(Long id);

    List<T> getAllEntities();

    List<T> getAllEntities(Integer from, Integer size);

    List<T> getEntitiesById(List<Long> ids);

}
