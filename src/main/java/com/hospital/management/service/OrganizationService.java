package com.hospital.management.service;

import com.hospital.management.domain.Organization;

import java.util.List;

/**
 * Service Interface for managing Organization.
 */
public interface OrganizationService {

    /**
     * Save a organization.
     *
     * @param organization the entity to save
     * @return the persisted entity
     */
    Organization save(Organization organization);

    /**
     *  Get all the organizations.
     *  
     *  @return the list of entities
     */
    List<Organization> findAll();

    /**
     *  Get the "id" organization.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Organization findOne(Long id);

    /**
     *  Delete the "id" organization.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
