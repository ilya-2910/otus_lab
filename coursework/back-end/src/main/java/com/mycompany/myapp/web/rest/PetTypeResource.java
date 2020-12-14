package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.PetType;
import com.mycompany.myapp.service.PetTypeService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.PetType}.
 */
@RestController
@RequestMapping("/api")
public class PetTypeResource {

    private final Logger log = LoggerFactory.getLogger(PetTypeResource.class);

    private static final String ENTITY_NAME = "petType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PetTypeService petTypeService;

    public PetTypeResource(PetTypeService petTypeService) {
        this.petTypeService = petTypeService;
    }

    /**
     * {@code POST  /pet-types} : Create a new petType.
     *
     * @param petType the petType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new petType, or with status {@code 400 (Bad Request)} if the petType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pet-types")
    public ResponseEntity<PetType> createPetType(@RequestBody PetType petType) throws URISyntaxException {
        log.debug("REST request to save PetType : {}", petType);
        if (petType.getId() != null) {
            throw new BadRequestAlertException("A new petType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PetType result = petTypeService.save(petType);
        return ResponseEntity.created(new URI("/api/pet-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pet-types} : Updates an existing petType.
     *
     * @param petType the petType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated petType,
     * or with status {@code 400 (Bad Request)} if the petType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the petType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pet-types")
    public ResponseEntity<PetType> updatePetType(@RequestBody PetType petType) throws URISyntaxException {
        log.debug("REST request to update PetType : {}", petType);
        if (petType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PetType result = petTypeService.save(petType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, petType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pet-types} : get all the petTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of petTypes in body.
     */
    @GetMapping("/pet-types")
    public List<PetType> getAllPetTypes() {
        log.debug("REST request to get all PetTypes");
        return petTypeService.findAll();
    }

    /**
     * {@code GET  /pet-types/:id} : get the "id" petType.
     *
     * @param id the id of the petType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the petType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pet-types/{id}")
    public ResponseEntity<PetType> getPetType(@PathVariable Long id) {
        log.debug("REST request to get PetType : {}", id);
        Optional<PetType> petType = petTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(petType);
    }

    /**
     * {@code DELETE  /pet-types/:id} : delete the "id" petType.
     *
     * @param id the id of the petType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pet-types/{id}")
    public ResponseEntity<Void> deletePetType(@PathVariable Long id) {
        log.debug("REST request to delete PetType : {}", id);

        petTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/pet-types?query=:query} : search for the petType corresponding
     * to the query.
     *
     * @param query the query of the petType search.
     * @return the result of the search.
     */
    @GetMapping("/_search/pet-types")
    public List<PetType> searchPetTypes(@RequestParam String query) {
        log.debug("REST request to search PetTypes for query {}", query);
        return petTypeService.search(query);
    }
}
