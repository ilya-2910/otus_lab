package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Visit;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Visit}.
 */
public interface VisitService {

    /**
     * Save a visit.
     *
     * @param visit the entity to save.
     * @return the persisted entity.
     */
    Visit save(Visit visit);

    /**
     * Get all the visits.
     *
     * @return the list of entities.
     */
    List<Visit> findAll();


    /**
     * Get the "id" visit.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Visit> findOne(Long id);

    /**
     * Delete the "id" visit.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    // custom user methods

    boolean isVisitTimeOverlap(Visit visit);
}
