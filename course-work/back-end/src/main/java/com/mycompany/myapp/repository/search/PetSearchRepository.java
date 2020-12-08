package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.Pet;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Pet} entity.
 */
public interface PetSearchRepository extends ElasticsearchRepository<Pet, Long> {
}
