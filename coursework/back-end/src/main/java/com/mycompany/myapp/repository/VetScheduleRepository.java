package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Vet;
import com.mycompany.myapp.domain.VetSchedule;

import com.mycompany.myapp.domain.Visit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data  repository for the VetSchedule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VetScheduleRepository extends JpaRepository<VetSchedule, Long>, JpaSpecificationExecutor<VetSchedule> {

    boolean existsByVetAndStartDateBeforeAndEndDateAfter(Vet vet, Instant startDate, Instant endDate);

}
