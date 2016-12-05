package com.hospital.management.service;

import com.hospital.management.domain.Poc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Poc.
 */
public interface PocService {

    /**
     * Save a poc.
     *
     * @param poc the entity to save
     * @return the persisted entity
     */
    Poc save(Poc poc);

    /**
     *  Get all the pocs.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Poc> findAll(Pageable pageable);

    /**
     *  Get the "id" poc.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Poc findOne(Long id);

    /**
     *  Delete the "id" poc.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
