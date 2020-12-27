package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.PetTypeService;
import com.mycompany.myapp.domain.PetType;
import com.mycompany.myapp.repository.PetTypeRepository;
import com.mycompany.myapp.service.dto.PetTypeDTO;
import com.mycompany.myapp.service.mapper.PetTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PetType}.
 */
@Service
@Transactional
public class PetTypeServiceImpl implements PetTypeService {

    private final Logger log = LoggerFactory.getLogger(PetTypeServiceImpl.class);

    private final PetTypeRepository petTypeRepository;

    private final PetTypeMapper petTypeMapper;

    public PetTypeServiceImpl(PetTypeRepository petTypeRepository, PetTypeMapper petTypeMapper) {
        this.petTypeRepository = petTypeRepository;
        this.petTypeMapper = petTypeMapper;
    }

    /**
     * Save a petType.
     *
     * @param petTypeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PetTypeDTO save(PetTypeDTO petTypeDTO) {
        log.debug("Request to save PetType : {}", petTypeDTO);
        PetType petType = petTypeMapper.toEntity(petTypeDTO);
        petType = petTypeRepository.save(petType);
        return petTypeMapper.toDto(petType);
    }

    /**
     * Get all the petTypes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PetTypeDTO> findAll() {
        log.debug("Request to get all PetTypes");
        return petTypeRepository.findAll().stream()
            .map(petTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one petType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PetTypeDTO> findOne(Long id) {
        log.debug("Request to get PetType : {}", id);
        return petTypeRepository.findById(id)
            .map(petTypeMapper::toDto);
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
