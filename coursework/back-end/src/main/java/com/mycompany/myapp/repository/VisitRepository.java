package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Visit;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Visit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {
}
