package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Vet;
import com.mycompany.myapp.domain.Visit;
import com.mycompany.myapp.service.VetScheduleService;
import com.mycompany.myapp.domain.VetSchedule;
import com.mycompany.myapp.repository.VetScheduleRepository;
import com.mycompany.myapp.service.dto.VetScheduleDTO;
import com.mycompany.myapp.service.dto.VisitDTO;
import com.mycompany.myapp.service.mapper.VetScheduleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link VetSchedule}.
 */
@Service
public class VetScheduleServiceImpl implements VetScheduleService {

    private final Logger log = LoggerFactory.getLogger(VetScheduleServiceImpl.class);

    private final VetScheduleRepository vetScheduleRepository;

    private final VetScheduleMapper vetScheduleMapper;

    public VetScheduleServiceImpl(VetScheduleRepository vetScheduleRepository, VetScheduleMapper vetScheduleMapper) {
        this.vetScheduleRepository = vetScheduleRepository;
        this.vetScheduleMapper = vetScheduleMapper;
    }

    /**
     * Save a vetSchedule.
     *
     * @param vetScheduleDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    @Transactional
    public VetScheduleDTO save(VetScheduleDTO vetScheduleDTO) {
        log.debug("Request to save VetSchedule : {}", vetScheduleDTO);
        VetSchedule vetSchedule = vetScheduleMapper.toEntity(vetScheduleDTO);
        vetSchedule = vetScheduleRepository.save(vetSchedule);
        return vetScheduleMapper.toDto(vetSchedule);
    }

    /**
     * Get all the vetSchedules.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<VetScheduleDTO> findAll() {
        log.debug("Request to get all VetSchedules");
        return vetScheduleRepository.findAll().stream()
            .map(vetScheduleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one vetSchedule by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<VetScheduleDTO> findOne(Long id) {
        log.debug("Request to get VetSchedule : {}", id);
        return vetScheduleRepository.findById(id)
            .map(vetScheduleMapper::toDto);
    }

    /**
     * Delete the vetSchedule by id.
     *
     * @param id the id of the entity.
     */
    @Override
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete VetSchedule : {}", id);

        vetScheduleRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isVetTimeAllow(VisitDTO visitDTO) {
        if (visitDTO.getVet() == null || visitDTO.getVet().getId() == null) return true;
        Vet vet = new Vet();
        vet.setId(visitDTO.getVet().getId());
        return vetScheduleRepository.existsByVetAndStartDateBeforeAndEndDateAfter(vet, visitDTO.getStartDate(), visitDTO.getEndDate());
    }

}
