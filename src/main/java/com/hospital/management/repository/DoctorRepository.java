package com.hospital.management.repository;

import com.hospital.management.domain.Doctor;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Doctor entity.
 */
@SuppressWarnings("unused")
public interface DoctorRepository extends JpaRepository<Doctor,Long> {

}
