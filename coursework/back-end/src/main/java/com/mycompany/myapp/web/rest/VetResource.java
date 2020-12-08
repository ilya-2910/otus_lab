package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Vet;
import com.mycompany.myapp.service.VetService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Vet}.
 */
@RestController
@RequestMapping("/api")
public class VetResource {

    private final Logger log = LoggerFactory.getLogger(VetResource.class);

    private static final String ENTITY_NAME = "vet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VetService vetService;

    public VetResource(VetService vetService) {
        this.vetService = vetService;
    }

    /**
     * {@code POST  /vets} : Create a new vet.
     *
     * @param vet the vet to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vet, or with status {@code 400 (Bad Request)} if the vet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vets")
    public ResponseEntity<Vet> createVet(@RequestBody Vet vet) throws URISyntaxException {
        log.debug("REST request to save Vet : {}", vet);
        if (vet.getId() != null) {
            throw new BadRequestAlertException("A new vet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Vet result = vetService.save(vet);
        return ResponseEntity.created(new URI("/api/vets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vets} : Updates an existing vet.
     *
     * @param vet the vet to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vet,
     * or with status {@code 400 (Bad Request)} if the vet is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vet couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vets")
    public ResponseEntity<Vet> updateVet(@RequestBody Vet vet) throws URISyntaxException {
        log.debug("REST request to update Vet : {}", vet);
        if (vet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Vet result = vetService.save(vet);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vet.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /vets} : get all the vets.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vets in body.
     */
    @GetMapping("/vets")
    public List<Vet> getAllVets() {
        log.debug("REST request to get all Vets");
        return vetService.findAll();
    }

    /**
     * {@code GET  /vets/:id} : get the "id" vet.
     *
     * @param id the id of the vet to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vet, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vets/{id}")
    public ResponseEntity<Vet> getVet(@PathVariable Long id) {
        log.debug("REST request to get Vet : {}", id);
        Optional<Vet> vet = vetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vet);
    }

    /**
     * {@code DELETE  /vets/:id} : delete the "id" vet.
     *
     * @param id the id of the vet to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vets/{id}")
    public ResponseEntity<Void> deleteVet(@PathVariable Long id) {
        log.debug("REST request to delete Vet : {}", id);

        vetService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/vets?query=:query} : search for the vet corresponding
     * to the query.
     *
     * @param query the query of the vet search.
     * @return the result of the search.
     */
    @GetMapping("/_search/vets")
    public List<Vet> searchVets(@RequestParam String query) {
        log.debug("REST request to search Vets for query {}", query);
        return vetService.search(query);
    }
}
