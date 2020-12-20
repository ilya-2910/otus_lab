package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.PetTypeService;
import com.mycompany.myapp.domain.PetType;
import com.mycompany.myapp.repository.PetTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link PetType}.
 */
@Service
@Transactional
public class PetTypeServiceImpl implements PetTypeService {

    private final Logger log = LoggerFactory.getLogger(PetTypeServiceImpl.class);

    private final PetTypeRepository petTypeRepository;

    public PetTypeServiceImpl(PetTypeRepository petTypeRepository) {
        this.petTypeRepository = petTypeRepository;
    }

    /**
     * Save a petType.
     *
     * @param petType the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PetType save(PetType petType) {
        log.debug("Request to save PetType : {}", petType);
        return petTypeRepository.save(petType);
    }

    /**
     * Get all the petTypes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PetType> findAll() {
        log.debug("Request to get all PetTypes");
        return petTypeRepository.findAll();
    }


    /**
     * Get one petType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PetType> findOne(Long id) {
        log.debug("Request to get PetType : {}", id);
        return petTypeRepository.findById(id);
    }

    /**
     * Delete the petType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PetType : {}", id);

        petTypeRepository.deleteById(id);
    }
}
