package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.Visit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Visit} entity.
 */
public interface VisitSearchRepository extends ElasticsearchRepository<Visit, Long> {
}
