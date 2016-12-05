package com.hospital.management.service.impl;

import com.hospital.management.service.OrganizationService;
import com.hospital.management.domain.Organization;
import com.hospital.management.repository.OrganizationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Organization.
 */
@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService{

    private final Logger log = LoggerFactory.getLogger(OrganizationServiceImpl.class);
    
    @Inject
    private OrganizationRepository organizationRepository;

    /**
     * Save a organization.
     *
     * @param organization the entity to save
     * @return the persisted entity
     */
    public Organization save(Organization organization) {
        log.debug("Request to save Organization : {}", organization);
        Organization result = organizationRepository.save(organization);
        return result;
    }

    /**
     *  Get all the organizations.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Organization> findAll() {
        log.debug("Request to get all Organizations");
        List<Organization> result = organizationRepository.findAll();

        return result;
    }

    /**
     *  Get one organization by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Organization findOne(Long id) {
        log.debug("Request to get Organization : {}", id);
        Organization organization = organizationRepository.findOne(id);
        return organization;
    }

    /**
     *  Delete the  organization by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Organization : {}", id);
        organizationRepository.delete(id);
    }
}
