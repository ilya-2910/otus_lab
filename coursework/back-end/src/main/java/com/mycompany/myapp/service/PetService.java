package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.PetDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Pet}.
 */
public interface PetService {

    /**
     * Save a pet.
     *
     * @param petDTO the entity to save.
     * @return the persisted entity.
     */
    PetDTO save(PetDTO petDTO);

    /**
     * Get all the pets.
     *
     * @return the list of entities.
     */
    List<PetDTO> findAll();


    /**
     * Get the "id" pet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PetDTO> findOne(Long id);

    /**
     * Delete the "id" pet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
