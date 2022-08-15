package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.CourseworkApp;
import com.mycompany.myapp.domain.VetSchedule;
import com.mycompany.myapp.domain.Vet;
import com.mycompany.myapp.repository.VetScheduleRepository;
import com.mycompany.myapp.service.VetScheduleService;
import com.mycompany.myapp.service.dto.VetScheduleDTO;
import com.mycompany.myapp.service.mapper.VetScheduleMapper;
import com.mycompany.myapp.service.dto.VetScheduleCriteria;
import com.mycompany.myapp.service.VetScheduleQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link VetScheduleResource} REST controller.
 */
@SpringBootTest(classes = CourseworkApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class VetScheduleResourceIT {

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private VetScheduleRepository vetScheduleRepository;

    @Autowired
    private VetScheduleMapper vetScheduleMapper;

    @Autowired
    private VetScheduleService vetScheduleService;

    @Autowired
    private VetScheduleQueryService vetScheduleQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVetScheduleMockMvc;

    private VetSchedule vetSchedule;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VetSchedule createEntity(EntityManager em) {
        VetSchedule vetSchedule = new VetSchedule()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE);
        return vetSchedule;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VetSchedule createUpdatedEntity(EntityManager em) {
        VetSchedule vetSchedule = new VetSchedule()
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        return vetSchedule;
    }

    @BeforeEach
    public void initTest() {
        vetSchedule = createEntity(em);
    }

    @Test
    @Transactional
    public void createVetSchedule() throws Exception {
        int databaseSizeBeforeCreate = vetScheduleRepository.findAll().size();
        // Create the VetSchedule
        VetScheduleDTO vetScheduleDTO = vetScheduleMapper.toDto(vetSchedule);
        restVetScheduleMockMvc.perform(post("/api/vet-schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vetScheduleDTO)))
            .andExpect(status().isCreated());

        // Validate the VetSchedule in the database
        List<VetSchedule> vetScheduleList = vetScheduleRepository.findAll();
        assertThat(vetScheduleList).hasSize(databaseSizeBeforeCreate + 1);
        VetSchedule testVetSchedule = vetScheduleList.get(vetScheduleList.size() - 1);
        assertThat(testVetSchedule.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testVetSchedule.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    public void createVetScheduleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vetScheduleRepository.findAll().size();

        // Create the VetSchedule with an existing ID
        vetSchedule.setId(1L);
        VetScheduleDTO vetScheduleDTO = vetScheduleMapper.toDto(vetSchedule);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVetScheduleMockMvc.perform(post("/api/vet-schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vetScheduleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VetSchedule in the database
        List<VetSchedule> vetScheduleList = vetScheduleRepository.findAll();
        assertThat(vetScheduleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllVetSchedules() throws Exception {
        // Initialize the database
        vetScheduleRepository.saveAndFlush(vetSchedule);

        // Get all the vetScheduleList
        restVetScheduleMockMvc.perform(get("/api/vet-schedules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vetSchedule.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getVetSchedule() throws Exception {
        // Initialize the database
        vetScheduleRepository.saveAndFlush(vetSchedule);

        // Get the vetSchedule
        restVetScheduleMockMvc.perform(get("/api/vet-schedules/{id}", vetSchedule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vetSchedule.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()));
    }


    @Test
    @Transactional
    public void getVetSchedulesByIdFiltering() throws Exception {
        // Initialize the database
        vetScheduleRepository.saveAndFlush(vetSchedule);

        Long id = vetSchedule.getId();

        defaultVetScheduleShouldBeFound("id.equals=" + id);
        defaultVetScheduleShouldNotBeFound("id.notEquals=" + id);

        defaultVetScheduleShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVetScheduleShouldNotBeFound("id.greaterThan=" + id);

        defaultVetScheduleShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVetScheduleShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllVetSchedulesByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        vetScheduleRepository.saveAndFlush(vetSchedule);

        // Get all the vetScheduleList where startDate equals to DEFAULT_START_DATE
        defaultVetScheduleShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the vetScheduleList where startDate equals to UPDATED_START_DATE
        defaultVetScheduleShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllVetSchedulesByStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vetScheduleRepository.saveAndFlush(vetSchedule);

        // Get all the vetScheduleList where startDate not equals to DEFAULT_START_DATE
        defaultVetScheduleShouldNotBeFound("startDate.notEquals=" + DEFAULT_START_DATE);

        // Get all the vetScheduleList where startDate not equals to UPDATED_START_DATE
        defaultVetScheduleShouldBeFound("startDate.notEquals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllVetSchedulesByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        vetScheduleRepository.saveAndFlush(vetSchedule);

        // Get all the vetScheduleList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultVetScheduleShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the vetScheduleList where startDate equals to UPDATED_START_DATE
        defaultVetScheduleShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllVetSchedulesByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        vetScheduleRepository.saveAndFlush(vetSchedule);

        // Get all the vetScheduleList where startDate is not null
        defaultVetScheduleShouldBeFound("startDate.specified=true");

        // Get all the vetScheduleList where startDate is null
        defaultVetScheduleShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllVetSchedulesByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        vetScheduleRepository.saveAndFlush(vetSchedule);

        // Get all the vetScheduleList where endDate equals to DEFAULT_END_DATE
        defaultVetScheduleShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the vetScheduleList where endDate equals to UPDATED_END_DATE
        defaultVetScheduleShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllVetSchedulesByEndDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vetScheduleRepository.saveAndFlush(vetSchedule);

        // Get all the vetScheduleList where endDate not equals to DEFAULT_END_DATE
        defaultVetScheduleShouldNotBeFound("endDate.notEquals=" + DEFAULT_END_DATE);

        // Get all the vetScheduleList where endDate not equals to UPDATED_END_DATE
        defaultVetScheduleShouldBeFound("endDate.notEquals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllVetSchedulesByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        vetScheduleRepository.saveAndFlush(vetSchedule);

        // Get all the vetScheduleList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultVetScheduleShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the vetScheduleList where endDate equals to UPDATED_END_DATE
        defaultVetScheduleShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllVetSchedulesByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        vetScheduleRepository.saveAndFlush(vetSchedule);

        // Get all the vetScheduleList where endDate is not null
        defaultVetScheduleShouldBeFound("endDate.specified=true");

        // Get all the vetScheduleList where endDate is null
        defaultVetScheduleShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllVetSchedulesByVetIsEqualToSomething() throws Exception {
        // Initialize the database
        vetScheduleRepository.saveAndFlush(vetSchedule);
        Vet vet = VetResourceIT.createEntity(em);
        em.persist(vet);
        em.flush();
        vetSchedule.setVet(vet);
        vetScheduleRepository.saveAndFlush(vetSchedule);
        Long vetId = vet.getId();

        // Get all the vetScheduleList where vet equals to vetId
        defaultVetScheduleShouldBeFound("vetId.equals=" + vetId);

        // Get all the vetScheduleList where vet equals to vetId + 1
        defaultVetScheduleShouldNotBeFound("vetId.equals=" + (vetId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVetScheduleShouldBeFound(String filter) throws Exception {
        restVetScheduleMockMvc.perform(get("/api/vet-schedules?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vetSchedule.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));

        // Check, that the count call also returns 1
        restVetScheduleMockMvc.perform(get("/api/vet-schedules/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVetScheduleShouldNotBeFound(String filter) throws Exception {
        restVetScheduleMockMvc.perform(get("/api/vet-schedules?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVetScheduleMockMvc.perform(get("/api/vet-schedules/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingVetSchedule() throws Exception {
        // Get the vetSchedule
        restVetScheduleMockMvc.perform(get("/api/vet-schedules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVetSchedule() throws Exception {
        // Initialize the database
        vetScheduleRepository.saveAndFlush(vetSchedule);

        int databaseSizeBeforeUpdate = vetScheduleRepository.findAll().size();

        // Update the vetSchedule
        VetSchedule updatedVetSchedule = vetScheduleRepository.findById(vetSchedule.getId()).get();
        // Disconnect from session so that the updates on updatedVetSchedule are not directly saved in db
        em.detach(updatedVetSchedule);
        updatedVetSchedule
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        VetScheduleDTO vetScheduleDTO = vetScheduleMapper.toDto(updatedVetSchedule);

        restVetScheduleMockMvc.perform(put("/api/vet-schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vetScheduleDTO)))
            .andExpect(status().isOk());

        // Validate the VetSchedule in the database
        List<VetSchedule> vetScheduleList = vetScheduleRepository.findAll();
        assertThat(vetScheduleList).hasSize(databaseSizeBeforeUpdate);
        VetSchedule testVetSchedule = vetScheduleList.get(vetScheduleList.size() - 1);
        assertThat(testVetSchedule.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testVetSchedule.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingVetSchedule() throws Exception {
        int databaseSizeBeforeUpdate = vetScheduleRepository.findAll().size();

        // Create the VetSchedule
        VetScheduleDTO vetScheduleDTO = vetScheduleMapper.toDto(vetSchedule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVetScheduleMockMvc.perform(put("/api/vet-schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vetScheduleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VetSchedule in the database
        List<VetSchedule> vetScheduleList = vetScheduleRepository.findAll();
        assertThat(vetScheduleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVetSchedule() throws Exception {
        // Initialize the database
        vetScheduleRepository.saveAndFlush(vetSchedule);

        int databaseSizeBeforeDelete = vetScheduleRepository.findAll().size();

        // Delete the vetSchedule
        restVetScheduleMockMvc.perform(delete("/api/vet-schedules/{id}", vetSchedule.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VetSchedule> vetScheduleList = vetScheduleRepository.findAll();
        assertThat(vetScheduleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
