package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.VetScheduleService;
import com.mycompany.myapp.domain.VetSchedule;
import com.mycompany.myapp.repository.VetScheduleRepository;
import com.mycompany.myapp.repository.search.VetScheduleSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link VetSchedule}.
 */
@Service
@Transactional
public class VetScheduleServiceImpl implements VetScheduleService {

    private final Logger log = LoggerFactory.getLogger(VetScheduleServiceImpl.class);

    private final VetScheduleRepository vetScheduleRepository;

    private final VetScheduleSearchRepository vetScheduleSearchRepository;

    public VetScheduleServiceImpl(VetScheduleRepository vetScheduleRepository, VetScheduleSearchRepository vetScheduleSearchRepository) {
        this.vetScheduleRepository = vetScheduleRepository;
        this.vetScheduleSearchRepository = vetScheduleSearchRepository;
    }

    /**
     * Save a vetSchedule.
     *
     * @param vetSchedule the entity to save.
     * @return the persisted entity.
     */
    @Override
    public VetSchedule save(VetSchedule vetSchedule) {
        log.debug("Request to save VetSchedule : {}", vetSchedule);
        VetSchedule result = vetScheduleRepository.save(vetSchedule);
        vetScheduleSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the vetSchedules.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<VetSchedule> findAll() {
        log.debug("Request to get all VetSchedules");
        return vetScheduleRepository.findAll();
    }


    /**
     * Get one vetSchedule by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<VetSchedule> findOne(Long id) {
        log.debug("Request to get VetSchedule : {}", id);
        return vetScheduleRepository.findById(id);
    }

    /**
     * Delete the vetSchedule by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete VetSchedule : {}", id);

        vetScheduleRepository.deleteById(id);
        vetScheduleSearchRepository.deleteById(id);
    }

    /**
     * Search for the vetSchedule corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<VetSchedule> search(String query) {
        log.debug("Request to search VetSchedules for query {}", query);
        return StreamSupport
            .stream(vetScheduleSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
