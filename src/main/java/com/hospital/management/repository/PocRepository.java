package com.hospital.management.repository;

import com.hospital.management.domain.Poc;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Poc entity.
 */
@SuppressWarnings("unused")
public interface PocRepository extends JpaRepository<Poc,Long> {

}
