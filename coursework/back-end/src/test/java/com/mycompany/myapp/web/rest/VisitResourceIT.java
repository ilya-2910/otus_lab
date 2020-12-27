package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.CourseworkApp;
import com.mycompany.myapp.domain.Visit;
import com.mycompany.myapp.domain.Pet;
import com.mycompany.myapp.domain.Vet;
import com.mycompany.myapp.repository.VisitRepository;
import com.mycompany.myapp.service.VisitService;
import com.mycompany.myapp.service.dto.VisitDTO;
import com.mycompany.myapp.service.mapper.VisitMapper;
import com.mycompany.myapp.service.dto.VisitCriteria;
import com.mycompany.myapp.service.VisitQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.domain.enumeration.VisitStatus;
/**
 * Integration tests for the {@link VisitResource} REST controller.
 */
@SpringBootTest(classes = CourseworkApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class VisitResourceIT {

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final VisitStatus DEFAULT_STATUS = VisitStatus.NEW;
    private static final VisitStatus UPDATED_STATUS = VisitStatus.DONE;

    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private VisitMapper visitMapper;

    @Autowired
    private VisitService visitService;

    @Autowired
    private VisitQueryService visitQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVisitMockMvc;

    private Visit visit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Visit createEntity(EntityManager em) {
        Visit visit = new Visit()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .description(DEFAULT_DESCRIPTION)
            .status(DEFAULT_STATUS);
        return visit;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Visit createUpdatedEntity(EntityManager em) {
        Visit visit = new Visit()
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS);
        return visit;
    }

    @BeforeEach
    public void initTest() {
        visit = createEntity(em);
    }

    @Test
    @Transactional
    public void createVisit() throws Exception {
        int databaseSizeBeforeCreate = visitRepository.findAll().size();
        // Create the Visit
        VisitDTO visitDTO = visitMapper.toDto(visit);
        restVisitMockMvc.perform(post("/api/visits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visitDTO)))
            .andExpect(status().isCreated());

        // Validate the Visit in the database
        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeCreate + 1);
        Visit testVisit = visitList.get(visitList.size() - 1);
        assertThat(testVisit.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testVisit.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testVisit.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testVisit.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createVisitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = visitRepository.findAll().size();

        // Create the Visit with an existing ID
        visit.setId(1L);
        VisitDTO visitDTO = visitMapper.toDto(visit);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVisitMockMvc.perform(post("/api/visits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Visit in the database
        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllVisits() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);

        // Get all the visitList
        restVisitMockMvc.perform(get("/api/visits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(visit.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getVisit() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);

        // Get the visit
        restVisitMockMvc.perform(get("/api/visits/{id}", visit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(visit.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }


    @Test
    @Transactional
    public void getVisitsByIdFiltering() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);

        Long id = visit.getId();

        defaultVisitShouldBeFound("id.equals=" + id);
        defaultVisitShouldNotBeFound("id.notEquals=" + id);

        defaultVisitShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVisitShouldNotBeFound("id.greaterThan=" + id);

        defaultVisitShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVisitShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllVisitsByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);

        // Get all the visitList where startDate equals to DEFAULT_START_DATE
        defaultVisitShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the visitList where startDate equals to UPDATED_START_DATE
        defaultVisitShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllVisitsByStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);

        // Get all the visitList where startDate not equals to DEFAULT_START_DATE
        defaultVisitShouldNotBeFound("startDate.notEquals=" + DEFAULT_START_DATE);

        // Get all the visitList where startDate not equals to UPDATED_START_DATE
        defaultVisitShouldBeFound("startDate.notEquals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllVisitsByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);

        // Get all the visitList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultVisitShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the visitList where startDate equals to UPDATED_START_DATE
        defaultVisitShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllVisitsByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);

        // Get all the visitList where startDate is not null
        defaultVisitShouldBeFound("startDate.specified=true");

        // Get all the visitList where startDate is null
        defaultVisitShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllVisitsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);

        // Get all the visitList where endDate equals to DEFAULT_END_DATE
        defaultVisitShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the visitList where endDate equals to UPDATED_END_DATE
        defaultVisitShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllVisitsByEndDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);

        // Get all the visitList where endDate not equals to DEFAULT_END_DATE
        defaultVisitShouldNotBeFound("endDate.notEquals=" + DEFAULT_END_DATE);

        // Get all the visitList where endDate not equals to UPDATED_END_DATE
        defaultVisitShouldBeFound("endDate.notEquals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllVisitsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);

        // Get all the visitList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultVisitShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the visitList where endDate equals to UPDATED_END_DATE
        defaultVisitShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllVisitsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);

        // Get all the visitList where endDate is not null
        defaultVisitShouldBeFound("endDate.specified=true");

        // Get all the visitList where endDate is null
        defaultVisitShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllVisitsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);

        // Get all the visitList where status equals to DEFAULT_STATUS
        defaultVisitShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the visitList where status equals to UPDATED_STATUS
        defaultVisitShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllVisitsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);

        // Get all the visitList where status not equals to DEFAULT_STATUS
        defaultVisitShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the visitList where status not equals to UPDATED_STATUS
        defaultVisitShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllVisitsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);

        // Get all the visitList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultVisitShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the visitList where status equals to UPDATED_STATUS
        defaultVisitShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllVisitsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);

        // Get all the visitList where status is not null
        defaultVisitShouldBeFound("status.specified=true");

        // Get all the visitList where status is null
        defaultVisitShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllVisitsByPetIsEqualToSomething() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);
        Pet pet = PetResourceIT.createEntity(em);
        em.persist(pet);
        em.flush();
        visit.setPet(pet);
        visitRepository.saveAndFlush(visit);
        Long petId = pet.getId();

        // Get all the visitList where pet equals to petId
        defaultVisitShouldBeFound("petId.equals=" + petId);

        // Get all the visitList where pet equals to petId + 1
        defaultVisitShouldNotBeFound("petId.equals=" + (petId + 1));
    }


    @Test
    @Transactional
    public void getAllVisitsByVetIsEqualToSomething() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);
        Vet vet = VetResourceIT.createEntity(em);
        em.persist(vet);
        em.flush();
        visit.setVet(vet);
        visitRepository.saveAndFlush(visit);
        Long vetId = vet.getId();

        // Get all the visitList where vet equals to vetId
        defaultVisitShouldBeFound("vetId.equals=" + vetId);

        // Get all the visitList where vet equals to vetId + 1
        defaultVisitShouldNotBeFound("vetId.equals=" + (vetId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVisitShouldBeFound(String filter) throws Exception {
        restVisitMockMvc.perform(get("/api/visits?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(visit.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restVisitMockMvc.perform(get("/api/visits/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVisitShouldNotBeFound(String filter) throws Exception {
        restVisitMockMvc.perform(get("/api/visits?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVisitMockMvc.perform(get("/api/visits/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingVisit() throws Exception {
        // Get the visit
        restVisitMockMvc.perform(get("/api/visits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVisit() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);

        int databaseSizeBeforeUpdate = visitRepository.findAll().size();

        // Update the visit
        Visit updatedVisit = visitRepository.findById(visit.getId()).get();
        // Disconnect from session so that the updates on updatedVisit are not directly saved in db
        em.detach(updatedVisit);
        updatedVisit
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS);
        VisitDTO visitDTO = visitMapper.toDto(updatedVisit);

        restVisitMockMvc.perform(put("/api/visits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visitDTO)))
            .andExpect(status().isOk());

        // Validate the Visit in the database
        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeUpdate);
        Visit testVisit = visitList.get(visitList.size() - 1);
        assertThat(testVisit.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testVisit.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testVisit.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testVisit.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingVisit() throws Exception {
        int databaseSizeBeforeUpdate = visitRepository.findAll().size();

        // Create the Visit
        VisitDTO visitDTO = visitMapper.toDto(visit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVisitMockMvc.perform(put("/api/visits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Visit in the database
        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVisit() throws Exception {
        // Initialize the database
        visitRepository.saveAndFlush(visit);

        int databaseSizeBeforeDelete = visitRepository.findAll().size();

        // Delete the visit
        restVisitMockMvc.perform(delete("/api/visits/{id}", visit.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Visit> visitList = visitRepository.findAll();
        assertThat(visitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
