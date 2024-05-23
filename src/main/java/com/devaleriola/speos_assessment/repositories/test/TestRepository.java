package com.devaleriola.speos_assessment.repositories.test;

import com.devaleriola.speos_assessment.entities.test.TestEntity;
import com.devaleriola.speos_assessment.repositories.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends GenericRepository<TestEntity> {
}
