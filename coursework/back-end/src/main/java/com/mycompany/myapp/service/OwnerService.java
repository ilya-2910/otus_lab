package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.OwnerDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Owner}.
 */
public interface OwnerService {

    /**
     * Save a owner.
     *
     * @param ownerDTO the entity to save.
     * @return the persisted entity.
     */
    OwnerDTO save(OwnerDTO ownerDTO);

    /**
     * Get all the owners.
     *
     * @return the list of entities.
     */
    List<OwnerDTO> findAll();


    /**
     * Get the "id" owner.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OwnerDTO> findOne(Long id);

    /**
     * Delete the "id" owner.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
