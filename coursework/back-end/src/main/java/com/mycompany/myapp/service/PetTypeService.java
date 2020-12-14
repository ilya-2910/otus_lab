package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.PetType;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link PetType}.
 */
public interface PetTypeService {

    /**
     * Save a petType.
     *
     * @param petType the entity to save.
     * @return the persisted entity.
     */
    PetType save(PetType petType);

    /**
     * Get all the petTypes.
     *
     * @return the list of entities.
     */
    List<PetType> findAll();


    /**
     * Get the "id" petType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PetType> findOne(Long id);

    /**
     * Delete the "id" petType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the petType corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<PetType> search(String query);
}
