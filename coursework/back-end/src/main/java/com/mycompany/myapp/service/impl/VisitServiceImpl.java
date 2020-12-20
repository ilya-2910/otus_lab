package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.VisitService;
import com.mycompany.myapp.domain.Visit;
import com.mycompany.myapp.repository.VisitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Visit}.
 */
@Service
@Transactional
public class VisitServiceImpl implements VisitService {

    private final Logger log = LoggerFactory.getLogger(VisitServiceImpl.class);

    private final VisitRepository visitRepository;

    public VisitServiceImpl(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    /**
     * Save a visit.
     *
     * @param visit the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Visit save(Visit visit) {
        log.debug("Request to save Visit : {}", visit);
        return visitRepository.save(visit);
    }

    /**
     * Get all the visits.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Visit> findAll() {
        log.debug("Request to get all Visits");
        return visitRepository.findAll();
    }


    /**
     * Get one visit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Visit> findOne(Long id) {
        log.debug("Request to get Visit : {}", id);
        return visitRepository.findById(id);
    }

    /**
     * Delete the visit by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Visit : {}", id);

        visitRepository.deleteById(id);
    }

    @Override
    public boolean isVisitTimeOverlap(Visit visit) {
        if (visit.getVet() == null) return true;

        List<Visit> visits = visitRepository.findByVetAndStartDateBeforeAndEndDateAfter(visit.getVet(), visit.getEndDate(), visit.getStartDate());
        return visits.stream()
            .filter(visit1 -> !visit1.getId().equals(visit.getId()))
            .findFirst().isPresent();
   }

}
