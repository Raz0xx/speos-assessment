package com.devaleriola.speos_assessment.repositories.partner;

import com.devaleriola.speos_assessment.entities.partner.PartnerEntity;
import com.devaleriola.speos_assessment.repositories.GenericRepository;

import java.util.List;

public interface PartnerRepository extends GenericRepository<PartnerEntity> {
    List<PartnerEntity> findByReference(String ref);

}
