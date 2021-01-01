package com.mycompany.myapp.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.mycompany.myapp.domain.VetSchedule;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.VetScheduleRepository;
import com.mycompany.myapp.service.dto.VetScheduleCriteria;
import com.mycompany.myapp.service.dto.VetScheduleDTO;
import com.mycompany.myapp.service.mapper.VetScheduleMapper;

/**
 * Service for executing complex queries for {@link VetSchedule} entities in the database.
 * The main input is a {@link VetScheduleCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VetScheduleDTO} or a {@link Page} of {@link VetScheduleDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VetScheduleQueryService extends QueryService<VetSchedule> {

    private final Logger log = LoggerFactory.getLogger(VetScheduleQueryService.class);

    private final VetScheduleRepository vetScheduleRepository;

    private final VetScheduleMapper vetScheduleMapper;

    public VetScheduleQueryService(VetScheduleRepository vetScheduleRepository, VetScheduleMapper vetScheduleMapper) {
        this.vetScheduleRepository = vetScheduleRepository;
        this.vetScheduleMapper = vetScheduleMapper;
    }

    /**
     * Return a {@link List} of {@link VetScheduleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VetScheduleDTO> findByCriteria(VetScheduleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<VetSchedule> specification = createSpecification(criteria);
        return vetScheduleMapper.toDto(vetScheduleRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link VetScheduleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VetScheduleDTO> findByCriteria(VetScheduleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<VetSchedule> specification = createSpecification(criteria);
        return vetScheduleRepository.findAll(specification, page)
            .map(vetScheduleMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VetScheduleCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<VetSchedule> specification = createSpecification(criteria);
        return vetScheduleRepository.count(specification);
    }

    /**
     * Function to convert {@link VetScheduleCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<VetSchedule> createSpecification(VetScheduleCriteria criteria) {
        Specification<VetSchedule> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), VetSchedule_.id));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), VetSchedule_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), VetSchedule_.endDate));
            }
            if (criteria.getVetId() != null) {
                specification = specification.and(buildSpecification(criteria.getVetId(),
                    root -> root.join(VetSchedule_.vet, JoinType.LEFT).get(Vet_.id)));
            }
        }
        return specification;
    }
}
