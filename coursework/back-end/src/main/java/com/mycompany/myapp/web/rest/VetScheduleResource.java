package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.VetScheduleService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.VetScheduleDTO;
import com.mycompany.myapp.service.dto.VetScheduleCriteria;
import com.mycompany.myapp.service.VetScheduleQueryService;

import io.github.jhipster.config.JHipsterProperties;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.VetSchedule}.
 */
@RestController
@RequestMapping("/api")
public class VetScheduleResource {

    private final Logger log = LoggerFactory.getLogger(VetScheduleResource.class);

    private static final String ENTITY_NAME = "vetSchedule";

//    @Value("${jhipster.clientApp.name}")
//    private String applicationName;
    private final JHipsterProperties jHipsterProperties;


    private final VetScheduleService vetScheduleService;

    private final VetScheduleQueryService vetScheduleQueryService;

    public VetScheduleResource(JHipsterProperties jHipsterProperties, VetScheduleService vetScheduleService, VetScheduleQueryService vetScheduleQueryService) {
        this.jHipsterProperties = jHipsterProperties;
        this.vetScheduleService = vetScheduleService;
        this.vetScheduleQueryService = vetScheduleQueryService;
    }

    /**
     * {@code POST  /vet-schedules} : Create a new vetSchedule.
     *
     * @param vetScheduleDTO the vetScheduleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vetScheduleDTO, or with status {@code 400 (Bad Request)} if the vetSchedule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vet-schedules")
    public ResponseEntity<VetScheduleDTO> createVetSchedule(@RequestBody VetScheduleDTO vetScheduleDTO) throws URISyntaxException {
        log.debug("REST request to save VetSchedule : {}", vetScheduleDTO);
        if (vetScheduleDTO.getId() != null) {
            throw new BadRequestAlertException("A new vetSchedule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VetScheduleDTO result = vetScheduleService.save(vetScheduleDTO);
        return ResponseEntity.created(new URI("/api/vet-schedules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(jHipsterProperties.getClientApp().getName(), true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vet-schedules} : Updates an existing vetSchedule.
     *
     * @param vetScheduleDTO the vetScheduleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vetScheduleDTO,
     * or with status {@code 400 (Bad Request)} if the vetScheduleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vetScheduleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vet-schedules")
    public ResponseEntity<VetScheduleDTO> updateVetSchedule(@RequestBody VetScheduleDTO vetScheduleDTO) throws URISyntaxException {
        log.debug("REST request to update VetSchedule : {}", vetScheduleDTO);
        if (vetScheduleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VetScheduleDTO result = vetScheduleService.save(vetScheduleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(jHipsterProperties.getClientApp().getName(), true, ENTITY_NAME, vetScheduleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /vet-schedules} : get all the vetSchedules.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vetSchedules in body.
     */
    @GetMapping("/vet-schedules")
    public ResponseEntity<List<VetScheduleDTO>> getAllVetSchedules(VetScheduleCriteria criteria) {
        log.debug("REST request to get VetSchedules by criteria: {}", criteria);
        List<VetScheduleDTO> entityList = vetScheduleQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /vet-schedules/count} : count all the vetSchedules.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/vet-schedules/count")
    public ResponseEntity<Long> countVetSchedules(VetScheduleCriteria criteria) {
        log.debug("REST request to count VetSchedules by criteria: {}", criteria);
        return ResponseEntity.ok().body(vetScheduleQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /vet-schedules/:id} : get the "id" vetSchedule.
     *
     * @param id the id of the vetScheduleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vetScheduleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vet-schedules/{id}")
    public ResponseEntity<VetScheduleDTO> getVetSchedule(@PathVariable Long id) {
        log.debug("REST request to get VetSchedule : {}", id);
        Optional<VetScheduleDTO> vetScheduleDTO = vetScheduleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vetScheduleDTO);
    }

    /**
     * {@code DELETE  /vet-schedules/:id} : delete the "id" vetSchedule.
     *
     * @param id the id of the vetScheduleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vet-schedules/{id}")
    public ResponseEntity<Void> deleteVetSchedule(@PathVariable Long id) {
        log.debug("REST request to delete VetSchedule : {}", id);

        vetScheduleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(jHipsterProperties.getClientApp().getName(), true, ENTITY_NAME, id.toString())).build();
    }
}
