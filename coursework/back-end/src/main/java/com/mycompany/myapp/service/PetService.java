package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Pet;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Pet}.
 */
public interface PetService {

    /**
     * Save a pet.
     *
     * @param pet the entity to save.
     * @return the persisted entity.
     */
    Pet save(Pet pet);

    /**
     * Get all the pets.
     *
     * @return the list of entities.
     */
    List<Pet> findAll();


    /**
     * Get the "id" pet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Pet> findOne(Long id);

    /**
     * Delete the "id" pet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
