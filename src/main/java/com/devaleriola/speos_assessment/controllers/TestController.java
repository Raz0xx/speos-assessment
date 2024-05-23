package com.devaleriola.speos_assessment.controllers;

import com.devaleriola.speos_assessment.entities.test.TestDto;
import com.devaleriola.speos_assessment.services.test.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tests")
public class TestController {

    @Autowired
    private TestService service;

    @RequestMapping
    public List<TestDto> getTests() {
        return service.getAllEntities();
    }
}
