package com.devaleriola.speos_assessment.services;

import com.devaleriola.speos_assessment.entities.GenericDto;
import com.devaleriola.speos_assessment.entities.GenericEntity;
import com.devaleriola.speos_assessment.repositories.GenericRepository;
import com.devaleriola.speos_assessment.utils.GenericMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.stream.Collectors;

public abstract class GenericServiceImpl<T extends GenericDto, U extends GenericEntity> implements GenericService<T> {

    private final GenericRepository<U> repository;
    @Autowired
    private GenericMapper mapper;
    private Method toDtoMethod, toEntityMethod;

    @Autowired
    public GenericServiceImpl(GenericRepository<U> repository) {
        this.repository = repository;
    }

    //only executed once as it's loaded as singleton
    //only impact of reflection is on initial load of the application
    @PostConstruct
    private void initializeMappingMethods() {
        this.toDtoMethod = this.getToDtoMethod();
        this.toEntityMethod = this.getToEntityMethod();
    }

    @Override
    public T saveEntity(T entity) {
        return this.toDto(this.repository.save(this.toEntity(entity)));
    }

    @Override
    public T getEntityById(long id) {
        //lazy loading
        return this.toDto(this.repository.getReferenceById(id));
    }

    @Override
    public List<T> getAllEntities() {
        return this.repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<T> getEntitiesById(List<Long> ids) {
        return this.repository.findByIdIn(ids).stream().map(this::toDto).collect(Collectors.toList());
    }

    //If service is extended with custom methods, we can access this.toDTO as well
    //instead of having the link with the GenericMapper again
    protected T toDto(U entity) {
        try {
            return (T) this.toDtoMethod.invoke(this.mapper, entity);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    //If service is extended with custom methods, we can access this.toEntity as well
    //instead of having the link with the GenericMapper again
    protected U toEntity(T dto) {
        try {
            return (U) this.toEntityMethod.invoke(this.mapper, dto);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    //search of "toDto" method in mapper for the selected entity
    private Method getToDtoMethod() {
        Class entityClass = (Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];

        try {
            return this.mapper.getClass().getMethod("toDto", entityClass);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }

    //search of "toEntity" method in mapper for the selected entity
    private Method getToEntityMethod() {
        Class dtoClass = (Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];

        try {
            return this.mapper.getClass().getMethod("toEntity", dtoClass);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }
}