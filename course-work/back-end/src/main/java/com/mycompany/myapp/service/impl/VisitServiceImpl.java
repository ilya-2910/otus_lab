package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.VisitService;
import com.mycompany.myapp.domain.Visit;
import com.mycompany.myapp.repository.VisitRepository;
import com.mycompany.myapp.repository.search.VisitSearchRepository;
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
 * Service Implementation for managing {@link Visit}.
 */
@Service
@Transactional
public class VisitServiceImpl implements VisitService {

    private final Logger log = LoggerFactory.getLogger(VisitServiceImpl.class);

    private final VisitRepository visitRepository;

    private final VisitSearchRepository visitSearchRepository;

    public VisitServiceImpl(VisitRepository visitRepository, VisitSearchRepository visitSearchRepository) {
        this.visitRepository = visitRepository;
        this.visitSearchRepository = visitSearchRepository;
    }

    /**
     * Save a visit.
     *
     * @param visit the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Visit save(Visit visit) {
        log.debug("Request to save Visit : {}", visit);
        Visit result = visitRepository.save(visit);
        visitSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the visits.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Visit> findAll() {
        log.debug("Request to get all Visits");
        return visitRepository.findAll();
    }


    /**
     * Get one visit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Visit> findOne(Long id) {
        log.debug("Request to get Visit : {}", id);
        return visitRepository.findById(id);
    }

    /**
     * Delete the visit by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Visit : {}", id);

        visitRepository.deleteById(id);
        visitSearchRepository.deleteById(id);
    }

    /**
     * Search for the visit corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Visit> search(String query) {
        log.debug("Request to search Visits for query {}", query);
        return StreamSupport
            .stream(visitSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
