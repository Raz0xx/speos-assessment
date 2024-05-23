package com.devaleriola.speos_assessment.repositories;

import com.devaleriola.speos_assessment.entities.GenericEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

//annotation might not be needed depending on the version of JpaRepository
@NoRepositoryBean
public interface GenericRepository<T extends GenericEntity> extends JpaRepository<T, Long> {

    List<T> findByIdIn(List<Long> ids);

}
