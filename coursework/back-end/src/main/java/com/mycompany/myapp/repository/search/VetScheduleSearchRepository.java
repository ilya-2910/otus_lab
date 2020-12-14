package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.VetSchedule;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link VetSchedule} entity.
 */
public interface VetScheduleSearchRepository extends ElasticsearchRepository<VetSchedule, Long> {
}
