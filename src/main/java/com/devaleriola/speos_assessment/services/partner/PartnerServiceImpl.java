package com.devaleriola.speos_assessment.services.partner;

import com.devaleriola.speos_assessment.entities.partner.PartnerBiz;
import com.devaleriola.speos_assessment.entities.partner.PartnerDto;
import com.devaleriola.speos_assessment.entities.partner.PartnerEntity;
import com.devaleriola.speos_assessment.exceptions.InvalidLocaleException;
import com.devaleriola.speos_assessment.exceptions.PartnerNotFoundException;
import com.devaleriola.speos_assessment.exceptions.ReferenceAlreadyUsedException;
import com.devaleriola.speos_assessment.exceptions.ResourceNotFoundException;
import com.devaleriola.speos_assessment.repositories.partner.PartnerRepository;
import com.devaleriola.speos_assessment.services.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartnerServiceImpl extends GenericServiceImpl<PartnerDto, PartnerEntity> implements PartnerService {

    @Autowired
    public PartnerServiceImpl(PartnerRepository repository) {
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

    @Override
    public PartnerDto createEntity(PartnerDto entity) {
        PartnerBiz partnerBiz = (PartnerBiz) entity;
        this.validatePartnerGeneralData(partnerBiz);
        this.validateCreationConstraints(partnerBiz);
        return super.createEntity(entity);
    }

    @Override
    public PartnerDto updateEntity(Long id, PartnerDto entity) {
        PartnerBiz partnerBiz = (PartnerBiz) entity;
        this.validatePartnerGeneralData(partnerBiz);
        this.validateUpdateConstraints(partnerBiz);
        return super.updateEntity(id, entity);
    }

    private void validatePartnerGeneralData(PartnerBiz partner) {
        if (!partner.hasValidLocale()) {
            throw new InvalidLocaleException(partner.getLocale().toString());
        }
    }

    private void validateCreationConstraints(PartnerBiz partner) {
        if (!((PartnerRepository) this.repository).findByReference(partner.getReference()).isEmpty()) {
            throw new ReferenceAlreadyUsedException(partner.getReference());
        }
    }

    private void validateUpdateConstraints(PartnerBiz partner) {
        List<PartnerEntity> existingPartners = ((PartnerRepository) this.repository).findByReference(partner.getReference());
        if (existingPartners.isEmpty()) {
            return;
        }

        PartnerDto existingPartner = this.toDto(existingPartners.get(0));
        if (!existingPartner.getReference().equals(partner.getReference())) {
            throw new ReferenceAlreadyUsedException(partner.getReference());
        }
    }
}
