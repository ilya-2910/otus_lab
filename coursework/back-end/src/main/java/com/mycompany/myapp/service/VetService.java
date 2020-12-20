package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Vet;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Vet}.
 */
public interface VetService {

    /**
     * Save a vet.
     *
     * @param vet the entity to save.
     * @return the persisted entity.
     */
    Vet save(Vet vet);

    /**
     * Get all the vets.
     *
     * @return the list of entities.
     */
    List<Vet> findAll();


    /**
     * Get the "id" vet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Vet> findOne(Long id);

    /**
     * Delete the "id" vet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
