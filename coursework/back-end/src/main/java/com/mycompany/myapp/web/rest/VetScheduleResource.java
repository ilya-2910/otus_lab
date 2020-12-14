package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.VetSchedule;
import com.mycompany.myapp.service.VetScheduleService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.VetSchedule}.
 */
@RestController
@RequestMapping("/api")
public class VetScheduleResource {

    private final Logger log = LoggerFactory.getLogger(VetScheduleResource.class);

    private static final String ENTITY_NAME = "vetSchedule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VetScheduleService vetScheduleService;

    public VetScheduleResource(VetScheduleService vetScheduleService) {
        this.vetScheduleService = vetScheduleService;
    }

    /**
     * {@code POST  /vet-schedules} : Create a new vetSchedule.
     *
     * @param vetSchedule the vetSchedule to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vetSchedule, or with status {@code 400 (Bad Request)} if the vetSchedule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vet-schedules")
    public ResponseEntity<VetSchedule> createVetSchedule(@RequestBody VetSchedule vetSchedule) throws URISyntaxException {
        log.debug("REST request to save VetSchedule : {}", vetSchedule);
        if (vetSchedule.getId() != null) {
            throw new BadRequestAlertException("A new vetSchedule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VetSchedule result = vetScheduleService.save(vetSchedule);
        return ResponseEntity.created(new URI("/api/vet-schedules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vet-schedules} : Updates an existing vetSchedule.
     *
     * @param vetSchedule the vetSchedule to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vetSchedule,
     * or with status {@code 400 (Bad Request)} if the vetSchedule is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vetSchedule couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vet-schedules")
    public ResponseEntity<VetSchedule> updateVetSchedule(@RequestBody VetSchedule vetSchedule) throws URISyntaxException {
        log.debug("REST request to update VetSchedule : {}", vetSchedule);
        if (vetSchedule.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VetSchedule result = vetScheduleService.save(vetSchedule);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vetSchedule.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /vet-schedules} : get all the vetSchedules.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vetSchedules in body.
     */
    @GetMapping("/vet-schedules")
    public List<VetSchedule> getAllVetSchedules() {
        log.debug("REST request to get all VetSchedules");
        return vetScheduleService.findAll();
    }

    /**
     * {@code GET  /vet-schedules/:id} : get the "id" vetSchedule.
     *
     * @param id the id of the vetSchedule to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vetSchedule, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vet-schedules/{id}")
    public ResponseEntity<VetSchedule> getVetSchedule(@PathVariable Long id) {
        log.debug("REST request to get VetSchedule : {}", id);
        Optional<VetSchedule> vetSchedule = vetScheduleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vetSchedule);
    }

    /**
     * {@code DELETE  /vet-schedules/:id} : delete the "id" vetSchedule.
     *
     * @param id the id of the vetSchedule to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vet-schedules/{id}")
    public ResponseEntity<Void> deleteVetSchedule(@PathVariable Long id) {
        log.debug("REST request to delete VetSchedule : {}", id);

        vetScheduleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/vet-schedules?query=:query} : search for the vetSchedule corresponding
     * to the query.
     *
     * @param query the query of the vetSchedule search.
     * @return the result of the search.
     */
    @GetMapping("/_search/vet-schedules")
    public List<VetSchedule> searchVetSchedules(@RequestParam String query) {
        log.debug("REST request to search VetSchedules for query {}", query);
        return vetScheduleService.search(query);
    }
}
