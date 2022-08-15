package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.PetService;
import com.mycompany.myapp.domain.Pet;
import com.mycompany.myapp.repository.PetRepository;
import com.mycompany.myapp.service.dto.PetDTO;
import com.mycompany.myapp.service.mapper.PetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Pet}.
 */
@Service
public class PetServiceImpl implements PetService {

    private final Logger log = LoggerFactory.getLogger(PetServiceImpl.class);

    private final PetRepository petRepository;

    private final PetMapper petMapper;

    public PetServiceImpl(PetRepository petRepository, PetMapper petMapper) {
        this.petRepository = petRepository;
        this.petMapper = petMapper;
    }

    /**
     * Save a pet.
     *
     * @param petDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    @Transactional
    public PetDTO save(PetDTO petDTO) {
        log.debug("Request to save Pet : {}", petDTO);
        Pet pet = petMapper.toEntity(petDTO);
        pet = petRepository.save(pet);
        return petMapper.toDto(pet);
    }

    /**
     * Get all the pets.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PetDTO> findAll() {
        log.debug("Request to get all Pets");
        return petRepository.findAll().stream()
            .map(petMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one pet by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PetDTO> findOne(Long id) {
        log.debug("Request to get Pet : {}", id);
        return petRepository.findById(id)
            .map(petMapper::toDto);
    }

    /**
     * Delete the pet by id.
     *
     * @param id the id of the entity.
     */
    @Override
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Pet : {}", id);

        petRepository.deleteById(id);
    }
}
