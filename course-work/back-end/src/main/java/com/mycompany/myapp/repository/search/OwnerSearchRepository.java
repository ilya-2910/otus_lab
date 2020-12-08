package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.Owner;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Owner} entity.
 */
public interface OwnerSearchRepository extends ElasticsearchRepository<Owner, Long> {
}
