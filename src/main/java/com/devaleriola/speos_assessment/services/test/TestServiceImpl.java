package com.devaleriola.speos_assessment.services.test;

import com.devaleriola.speos_assessment.entities.test.TestDto;
import com.devaleriola.speos_assessment.entities.test.TestEntity;
import com.devaleriola.speos_assessment.repositories.test.TestRepository;
import com.devaleriola.speos_assessment.services.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl extends GenericServiceImpl<TestDto, TestEntity> implements TestService {

    @Autowired
    public TestServiceImpl(TestRepository repository) {
        super(repository);
    }
}
