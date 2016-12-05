package com.hospital.management.service.impl;

import com.hospital.management.service.DoctorService;
import com.hospital.management.domain.Doctor;
import com.hospital.management.repository.DoctorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Doctor.
 */
@Service
@Transactional
public class DoctorServiceImpl implements DoctorService{

    private final Logger log = LoggerFactory.getLogger(DoctorServiceImpl.class);
    
    @Inject
    private DoctorRepository doctorRepository;

    /**
     * Save a doctor.
     *
     * @param doctor the entity to save
     * @return the persisted entity
     */
    public Doctor save(Doctor doctor) {
        log.debug("Request to save Doctor : {}", doctor);
        Doctor result = doctorRepository.save(doctor);
        return result;
    }

    /**
     *  Get all the doctors.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Doctor> findAll() {
        log.debug("Request to get all Doctors");
        List<Doctor> result = doctorRepository.findAll();

        return result;
    }

    /**
     *  Get one doctor by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Doctor findOne(Long id) {
        log.debug("Request to get Doctor : {}", id);
        Doctor doctor = doctorRepository.findOne(id);
        return doctor;
    }

    /**
     *  Delete the  doctor by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Doctor : {}", id);
        doctorRepository.delete(id);
    }
}
