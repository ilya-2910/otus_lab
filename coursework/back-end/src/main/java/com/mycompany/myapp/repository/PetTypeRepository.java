package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PetType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PetType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PetTypeRepository extends JpaRepository<PetType, Long> {
}
