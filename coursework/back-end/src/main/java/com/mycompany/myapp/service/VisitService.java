package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.VisitDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Visit}.
 */
public interface VisitService {

    /**
     * Save a visit.
     *
     * @param visitDTO the entity to save.
     * @return the persisted entity.
     */
    VisitDTO save(VisitDTO visitDTO);

    /**
     * Get all the visits.
     *
     * @return the list of entities.
     */
    List<VisitDTO> findAll();


    /**
     * Get the "id" visit.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VisitDTO> findOne(Long id);

    /**
     * Delete the "id" visit.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    // custom user methods

    boolean isVisitTimeOverlap(VisitDTO visit);
}
