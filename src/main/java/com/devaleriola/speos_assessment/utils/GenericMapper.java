package com.devaleriola.speos_assessment.utils;

import com.devaleriola.speos_assessment.entities.partner.Partner;
import com.devaleriola.speos_assessment.entities.partner.PartnerDto;
import com.devaleriola.speos_assessment.entities.partner.PartnerEntity;
import com.devaleriola.speos_assessment.entities.test.Test;
import com.devaleriola.speos_assessment.entities.test.TestDto;
import com.devaleriola.speos_assessment.entities.test.TestEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenericMapper {

    Test toDto(TestEntity entity);

    TestEntity toEntity(TestDto dto);

    Partner toDto(PartnerEntity entity);

    PartnerEntity toEntity(PartnerDto dto);
}
