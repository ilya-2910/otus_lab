package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.Vet;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Vet} entity.
 */
public interface VetSearchRepository extends ElasticsearchRepository<Vet, Long> {
}
