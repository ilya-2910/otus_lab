package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.OwnerService;
import com.mycompany.myapp.domain.Owner;
import com.mycompany.myapp.repository.OwnerRepository;
import com.mycompany.myapp.repository.search.OwnerSearchRepository;
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
 * Service Implementation for managing {@link Owner}.
 */
@Service
@Transactional
public class OwnerServiceImpl implements OwnerService {

    private final Logger log = LoggerFactory.getLogger(OwnerServiceImpl.class);

    private final OwnerRepository ownerRepository;

    private final OwnerSearchRepository ownerSearchRepository;

    public OwnerServiceImpl(OwnerRepository ownerRepository, OwnerSearchRepository ownerSearchRepository) {
        this.ownerRepository = ownerRepository;
        this.ownerSearchRepository = ownerSearchRepository;
    }

    /**
     * Save a owner.
     *
     * @param owner the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Owner save(Owner owner) {
        log.debug("Request to save Owner : {}", owner);
        Owner result = ownerRepository.save(owner);
        ownerSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the owners.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Owner> findAll() {
        log.debug("Request to get all Owners");
        return ownerRepository.findAll();
    }


    /**
     * Get one owner by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Owner> findOne(Long id) {
        log.debug("Request to get Owner : {}", id);
        return ownerRepository.findById(id);
    }

    /**
     * Delete the owner by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Owner : {}", id);

        ownerRepository.deleteById(id);
        ownerSearchRepository.deleteById(id);
    }

    /**
     * Search for the owner corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Owner> search(String query) {
        log.debug("Request to search Owners for query {}", query);
        return StreamSupport
            .stream(ownerSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
