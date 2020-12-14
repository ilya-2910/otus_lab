package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.VetSchedule;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link VetSchedule}.
 */
public interface VetScheduleService {

    /**
     * Save a vetSchedule.
     *
     * @param vetSchedule the entity to save.
     * @return the persisted entity.
     */
    VetSchedule save(VetSchedule vetSchedule);

    /**
     * Get all the vetSchedules.
     *
     * @return the list of entities.
     */
    List<VetSchedule> findAll();


    /**
     * Get the "id" vetSchedule.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VetSchedule> findOne(Long id);

    /**
     * Delete the "id" vetSchedule.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the vetSchedule corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<VetSchedule> search(String query);
}
