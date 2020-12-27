package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.VetScheduleDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.VetSchedule}.
 */
public interface VetScheduleService {

    /**
     * Save a vetSchedule.
     *
     * @param vetScheduleDTO the entity to save.
     * @return the persisted entity.
     */
    VetScheduleDTO save(VetScheduleDTO vetScheduleDTO);

    /**
     * Get all the vetSchedules.
     *
     * @return the list of entities.
     */
    List<VetScheduleDTO> findAll();


    /**
     * Get the "id" vetSchedule.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VetScheduleDTO> findOne(Long id);

    /**
     * Delete the "id" vetSchedule.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
