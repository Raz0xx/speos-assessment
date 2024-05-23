package com.devaleriola.speos_assessment.services;

import com.devaleriola.speos_assessment.entities.GenericDto;
import com.devaleriola.speos_assessment.entities.GenericEntity;
import com.devaleriola.speos_assessment.exceptions.InvalidPageFromException;
import com.devaleriola.speos_assessment.exceptions.InvalidPageSizeException;
import com.devaleriola.speos_assessment.exceptions.ResourceNotFoundException;
import com.devaleriola.speos_assessment.models.OffsetBasedPageRequest;
import com.devaleriola.speos_assessment.repositories.GenericRepository;
import com.devaleriola.speos_assessment.utils.GenericMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class GenericServiceImpl<T extends GenericDto, U extends GenericEntity> implements GenericService<T> {

    protected final GenericRepository<U> repository;
    @Autowired
    private GenericMapper mapper;
    private Method toDtoMethod, toEntityMethod;
    @Value("${database.default_page_size}")
    private int defaultPageSize;

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
    public T createEntity(T entity) {
        return this.toDto(this.repository.save(this.toEntity(entity)));
    }

    @Override
    public T updateEntity(Long id, T entity) {
        return this.toDto(this.repository.save(this.toEntity(entity)));
    }

    @Override
    public T getEntityById(Long id) {
        Optional<U> entity = this.repository.findById(id);
        if (entity.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return this.toDto(entity.get());
    }

    @Override
    public List<T> getAllEntities() {
        return this.repository
                .findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<T> getAllEntities(Integer from, Integer size) {
        var pageSize = size == null ? defaultPageSize : size;
        if (pageSize < 1) {
            throw new InvalidPageSizeException();
        }

        var pageFrom = from == null ? 0 : from;
        if (pageFrom < 0) {
            throw new InvalidPageFromException();
        }

        return this.repository
                .findAll(new OffsetBasedPageRequest(pageSize, pageFrom))
                .getContent()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
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
