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
import com.devaleriola.speos_assessment.utils.GenericMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PartnerServiceImpl extends GenericServiceImpl<PartnerDto, PartnerEntity> implements PartnerService {

    @Autowired
    private GenericMapper mapper;

    @Autowired
    public PartnerServiceImpl(PartnerRepository repository) {
        super(repository);
    }

    @Override
    @Transactional
    public PartnerDto getEntityById(Long id) {
        try {
            return super.getEntityById(id);
        } catch (ResourceNotFoundException exception) {
            throw new PartnerNotFoundException(id);
        }
    }

    @Override
    @Transactional
    public PartnerDto createEntity(PartnerDto entity) {
        //Business validations
        PartnerBiz partnerBiz = (PartnerBiz) entity;
        this.validatePartnerGeneralData(partnerBiz);

        //DB-related validations
        this.validateCreationConstraints(entity);

        return super.createEntity(entity);
    }

    @Override
    @Transactional
    public PartnerDto updateEntity(Long id, PartnerDto entity) {
        entity.setId(id);

        //Business validations
        PartnerBiz partnerBiz = (PartnerBiz) entity;
        this.validatePartnerGeneralData(partnerBiz);

        //Allow for both PUT and MERGE operations by fetching back the original record
        //and updating the changed fields only
        PartnerDto existingPartner;
        try {
            existingPartner = this.getEntityById(id);
        } catch (ResourceNotFoundException exception) {
            throw new PartnerNotFoundException(id);
        }

        //DB-related validations
        this.validateUpdateConstraints(existingPartner, entity);

        mapper.updatePartnerFromPartner(existingPartner, entity);

        //Don't call super method as entity has already been retrieved from DB
        return this.toDto(this.repository.save(this.toEntity(existingPartner)));
    }

    @Override
    @Transactional
    public void deleteEntity(Long id) {
        try {
            super.deleteEntity(id);
        } catch (ResourceNotFoundException exception) {
            throw new PartnerNotFoundException(id);
        }
    }

    private void validatePartnerGeneralData(PartnerBiz partner) {
        if (!partner.hasValidLocale()) {
            throw new InvalidLocaleException(partner.getLocale().toString());
        }
    }

    private void validateCreationConstraints(PartnerDto partner) {
        if (!((PartnerRepository) this.repository).findByReference(partner.getReference()).isEmpty()) {
            throw new ReferenceAlreadyUsedException(partner.getReference());
        }
    }

    private void validateUpdateConstraints(PartnerDto oldPartner, PartnerDto newPartner) {
        if (!oldPartner.getReference().equals(newPartner.getReference())) {
            List<PartnerEntity> existingPartners = ((PartnerRepository) this.repository).findByReference(newPartner.getReference());
            if (!existingPartners.isEmpty() && existingPartners.get(0).getId() != newPartner.getId()) {
                throw new ReferenceAlreadyUsedException(newPartner.getReference());
            }
        }
    }
}
