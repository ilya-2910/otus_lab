package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.VisitService;
import com.mycompany.myapp.domain.Visit;
import com.mycompany.myapp.repository.VisitRepository;
import com.mycompany.myapp.service.dto.VisitDTO;
import com.mycompany.myapp.service.mapper.VisitMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Visit}.
 */
@Service
public class VisitServiceImpl implements VisitService {

    private final Logger log = LoggerFactory.getLogger(VisitServiceImpl.class);

    private final VisitRepository visitRepository;

    private final VisitMapper visitMapper;

    public VisitServiceImpl(VisitRepository visitRepository, VisitMapper visitMapper) {
        this.visitRepository = visitRepository;
        this.visitMapper = visitMapper;
    }

    /**
     * Save a visit.
     *
     * @param visitDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    @Transactional
    public VisitDTO save(VisitDTO visitDTO) {
        log.debug("Request to save Visit : {}", visitDTO);
        Visit visit = visitMapper.toEntity(visitDTO);
        visit = visitRepository.save(visit);
        return visitMapper.toDto(visit);
    }

    /**
     * Get all the visits.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<VisitDTO> findAll() {
        log.debug("Request to get all Visits");
        return visitRepository.findAll().stream()
            .map(visitMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one visit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<VisitDTO> findOne(Long id) {
        log.debug("Request to get Visit : {}", id);
        return visitRepository.findById(id)
            .map(visitMapper::toDto);
    }

    /**
     * Delete the visit by id.
     *
     * @param id the id of the entity.
     */
    @Override
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Visit : {}", id);

        visitRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isVisitTimeOverlap(VisitDTO visitDTO) {
        if (visitDTO.getVet() == null && visitDTO.getVet().getId() == null) return true;
        Visit visit = visitMapper.toEntity(visitDTO);

        Visit existVisit = visitRepository.findFirstByVetAndStartDateBeforeAndEndDateAfter(visit.getVet(), visit.getEndDate(), visit.getStartDate());
        return existVisit != null && !existVisit.getId().equals(visit.getId());
   }

}
