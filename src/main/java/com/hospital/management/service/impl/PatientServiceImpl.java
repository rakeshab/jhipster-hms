package com.hospital.management.service.impl;

import com.hospital.management.service.PatientService;
import com.hospital.management.domain.Patient;
import com.hospital.management.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Patient.
 */
@Service
@Transactional
public class PatientServiceImpl implements PatientService{

    private final Logger log = LoggerFactory.getLogger(PatientServiceImpl.class);
    
    @Inject
    private PatientRepository patientRepository;

    /**
     * Save a patient.
     *
     * @param patient the entity to save
     * @return the persisted entity
     */
    public Patient save(Patient patient) {
        log.debug("Request to save Patient : {}", patient);
        Patient result = patientRepository.save(patient);
        return result;
    }

    /**
     *  Get all the patients.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Patient> findAll() {
        log.debug("Request to get all Patients");
        List<Patient> result = patientRepository.findAll();

        return result;
    }

    /**
     *  Get one patient by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Patient findOne(Long id) {
        log.debug("Request to get Patient : {}", id);
        Patient patient = patientRepository.findOne(id);
        return patient;
    }

    /**
     *  Delete the  patient by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Patient : {}", id);
        patientRepository.delete(id);
    }
}
