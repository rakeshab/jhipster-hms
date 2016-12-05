package com.hospital.management.service.impl;

import com.hospital.management.service.PocService;
import com.hospital.management.domain.Poc;
import com.hospital.management.repository.PocRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Poc.
 */
@Service
@Transactional
public class PocServiceImpl implements PocService{

    private final Logger log = LoggerFactory.getLogger(PocServiceImpl.class);
    
    @Inject
    private PocRepository pocRepository;

    /**
     * Save a poc.
     *
     * @param poc the entity to save
     * @return the persisted entity
     */
    public Poc save(Poc poc) {
        log.debug("Request to save Poc : {}", poc);
        Poc result = pocRepository.save(poc);
        return result;
    }

    /**
     *  Get all the pocs.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Poc> findAll(Pageable pageable) {
        log.debug("Request to get all Pocs");
        Page<Poc> result = pocRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one poc by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Poc findOne(Long id) {
        log.debug("Request to get Poc : {}", id);
        Poc poc = pocRepository.findOne(id);
        return poc;
    }

    /**
     *  Delete the  poc by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Poc : {}", id);
        pocRepository.delete(id);
    }
}
