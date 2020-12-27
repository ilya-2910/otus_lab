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

import com.mycompany.myapp.domain.Visit;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.VisitRepository;
import com.mycompany.myapp.service.dto.VisitCriteria;
import com.mycompany.myapp.service.dto.VisitDTO;
import com.mycompany.myapp.service.mapper.VisitMapper;

/**
 * Service for executing complex queries for {@link Visit} entities in the database.
 * The main input is a {@link VisitCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VisitDTO} or a {@link Page} of {@link VisitDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VisitQueryService extends QueryService<Visit> {

    private final Logger log = LoggerFactory.getLogger(VisitQueryService.class);

    private final VisitRepository visitRepository;

    private final VisitMapper visitMapper;

    public VisitQueryService(VisitRepository visitRepository, VisitMapper visitMapper) {
        this.visitRepository = visitRepository;
        this.visitMapper = visitMapper;
    }

    /**
     * Return a {@link List} of {@link VisitDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VisitDTO> findByCriteria(VisitCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Visit> specification = createSpecification(criteria);
        return visitMapper.toDto(visitRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link VisitDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VisitDTO> findByCriteria(VisitCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Visit> specification = createSpecification(criteria);
        return visitRepository.findAll(specification, page)
            .map(visitMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VisitCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Visit> specification = createSpecification(criteria);
        return visitRepository.count(specification);
    }

    /**
     * Function to convert {@link VisitCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Visit> createSpecification(VisitCriteria criteria) {
        Specification<Visit> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Visit_.id));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), Visit_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), Visit_.endDate));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Visit_.status));
            }
            if (criteria.getPetId() != null) {
                specification = specification.and(buildSpecification(criteria.getPetId(),
                    root -> root.join(Visit_.pet, JoinType.LEFT).get(Pet_.id)));
            }
            if (criteria.getVetId() != null) {
                specification = specification.and(buildSpecification(criteria.getVetId(),
                    root -> root.join(Visit_.vet, JoinType.LEFT).get(Vet_.id)));
            }
        }
        return specification;
    }
}
