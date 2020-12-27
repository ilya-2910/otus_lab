package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.CourseworkApp;
import com.mycompany.myapp.domain.VetSchedule;
import com.mycompany.myapp.repository.VetScheduleRepository;
import com.mycompany.myapp.service.VetScheduleService;
import com.mycompany.myapp.service.dto.VetScheduleDTO;
import com.mycompany.myapp.service.mapper.VetScheduleMapper;

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
