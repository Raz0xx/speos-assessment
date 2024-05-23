package com.devaleriola.speos_assessment.services.partner;

import com.devaleriola.speos_assessment.entities.partner.PartnerDto;
import com.devaleriola.speos_assessment.entities.partner.PartnerEntity;
import com.devaleriola.speos_assessment.exceptions.PartnerNotFoundException;
import com.devaleriola.speos_assessment.exceptions.ResourceNotFoundException;
import com.devaleriola.speos_assessment.repositories.GenericRepository;
import com.devaleriola.speos_assessment.services.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartnerServiceImpl extends GenericServiceImpl<PartnerDto, PartnerEntity> implements PartnerService {

    @Autowired
    public PartnerServiceImpl(GenericRepository<PartnerEntity> repository) {
        super(repository);
    }

    @Override
    public PartnerDto getEntityById(Long id) {
        try {
            return super.getEntityById(id);
        } catch (ResourceNotFoundException exception) {
            throw new PartnerNotFoundException(id);
        }
    }
}
