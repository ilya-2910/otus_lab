package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.VetService;
import com.mycompany.myapp.domain.Vet;
import com.mycompany.myapp.repository.VetRepository;
import com.mycompany.myapp.service.dto.VetDTO;
import com.mycompany.myapp.service.mapper.VetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Vet}.
 */
@Service
@Transactional
public class VetServiceImpl implements VetService {

    private final Logger log = LoggerFactory.getLogger(VetServiceImpl.class);

    private final VetRepository vetRepository;

    private final VetMapper vetMapper;

    public VetServiceImpl(VetRepository vetRepository, VetMapper vetMapper) {
        this.vetRepository = vetRepository;
        this.vetMapper = vetMapper;
    }

    /**
     * Save a vet.
     *
     * @param vetDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public VetDTO save(VetDTO vetDTO) {
        log.debug("Request to save Vet : {}", vetDTO);
        Vet vet = vetMapper.toEntity(vetDTO);
        vet = vetRepository.save(vet);
        return vetMapper.toDto(vet);
    }

    /**
     * Get all the vets.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<VetDTO> findAll() {
        log.debug("Request to get all Vets");
        return vetRepository.findAll().stream()
            .map(vetMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one vet by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<VetDTO> findOne(Long id) {
        log.debug("Request to get Vet : {}", id);
        return vetRepository.findById(id)
            .map(vetMapper::toDto);
    }

    /**
     * Delete the vet by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Vet : {}", id);

        vetRepository.deleteById(id);
    }
}
