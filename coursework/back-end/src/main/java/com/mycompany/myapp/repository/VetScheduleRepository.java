package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.VetSchedule;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the VetSchedule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VetScheduleRepository extends JpaRepository<VetSchedule, Long> {
}
