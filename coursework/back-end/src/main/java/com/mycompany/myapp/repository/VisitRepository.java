package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Vet;
import com.mycompany.myapp.domain.Visit;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data  repository for the Visit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

    /**
     * overlap 2 dates (standard function)
     *
     * @param vet
     * @param startDate
     * @param endDate
     * @return
     */
    List<Visit> findByVetAndStartDateBeforeAndEndDateAfter(Vet vet, Instant startDate, Instant endDate);

}
