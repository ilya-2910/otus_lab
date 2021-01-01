package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.PetTypeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.PetType}.
 */
public interface PetTypeService {

    /**
     * Save a petType.
     *
     * @param petTypeDTO the entity to save.
     * @return the persisted entity.
     */
    PetTypeDTO save(PetTypeDTO petTypeDTO);

    /**
     * Get all the petTypes.
     *
     * @return the list of entities.
     */
    List<PetTypeDTO> findAll();


    /**
     * Get the "id" petType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PetTypeDTO> findOne(Long id);

    /**
     * Delete the "id" petType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
