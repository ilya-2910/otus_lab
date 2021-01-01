package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Vet;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Vet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VetRepository extends JpaRepository<Vet, Long> {
}
