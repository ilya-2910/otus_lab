package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.CourseworkApp;
import com.mycompany.myapp.domain.PetType;
import com.mycompany.myapp.repository.PetTypeRepository;
import com.mycompany.myapp.service.PetTypeService;
import com.mycompany.myapp.service.dto.PetTypeDTO;
import com.mycompany.myapp.service.mapper.PetTypeMapper;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PetTypeResource} REST controller.
 */
@SpringBootTest(classes = CourseworkApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PetTypeResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private PetTypeRepository petTypeRepository;

    @Autowired
    private PetTypeMapper petTypeMapper;

    @Autowired
    private PetTypeService petTypeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPetTypeMockMvc;

    private PetType petType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PetType createEntity(EntityManager em) {
        PetType petType = new PetType()
            .type(DEFAULT_TYPE);
        return petType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PetType createUpdatedEntity(EntityManager em) {
        PetType petType = new PetType()
            .type(UPDATED_TYPE);
        return petType;
    }

    @BeforeEach
    public void initTest() {
        petType = createEntity(em);
    }

    @Test
    @Transactional
    public void createPetType() throws Exception {
        int databaseSizeBeforeCreate = petTypeRepository.findAll().size();
        // Create the PetType
        PetTypeDTO petTypeDTO = petTypeMapper.toDto(petType);
        restPetTypeMockMvc.perform(post("/api/pet-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(petTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the PetType in the database
        List<PetType> petTypeList = petTypeRepository.findAll();
        assertThat(petTypeList).hasSize(databaseSizeBeforeCreate + 1);
        PetType testPetType = petTypeList.get(petTypeList.size() - 1);
        assertThat(testPetType.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createPetTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = petTypeRepository.findAll().size();

        // Create the PetType with an existing ID
        petType.setId(1L);
        PetTypeDTO petTypeDTO = petTypeMapper.toDto(petType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPetTypeMockMvc.perform(post("/api/pet-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(petTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PetType in the database
        List<PetType> petTypeList = petTypeRepository.findAll();
        assertThat(petTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPetTypes() throws Exception {
        // Initialize the database
        petTypeRepository.saveAndFlush(petType);

        // Get all the petTypeList
        restPetTypeMockMvc.perform(get("/api/pet-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(petType.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }
    
    @Test
    @Transactional
    public void getPetType() throws Exception {
        // Initialize the database
        petTypeRepository.saveAndFlush(petType);

        // Get the petType
        restPetTypeMockMvc.perform(get("/api/pet-types/{id}", petType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(petType.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }
    @Test
    @Transactional
    public void getNonExistingPetType() throws Exception {
        // Get the petType
        restPetTypeMockMvc.perform(get("/api/pet-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePetType() throws Exception {
        // Initialize the database
        petTypeRepository.saveAndFlush(petType);

        int databaseSizeBeforeUpdate = petTypeRepository.findAll().size();

        // Update the petType
        PetType updatedPetType = petTypeRepository.findById(petType.getId()).get();
        // Disconnect from session so that the updates on updatedPetType are not directly saved in db
        em.detach(updatedPetType);
        updatedPetType
            .type(UPDATED_TYPE);
        PetTypeDTO petTypeDTO = petTypeMapper.toDto(updatedPetType);

        restPetTypeMockMvc.perform(put("/api/pet-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(petTypeDTO)))
            .andExpect(status().isOk());

        // Validate the PetType in the database
        List<PetType> petTypeList = petTypeRepository.findAll();
        assertThat(petTypeList).hasSize(databaseSizeBeforeUpdate);
        PetType testPetType = petTypeList.get(petTypeList.size() - 1);
        assertThat(testPetType.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingPetType() throws Exception {
        int databaseSizeBeforeUpdate = petTypeRepository.findAll().size();

        // Create the PetType
        PetTypeDTO petTypeDTO = petTypeMapper.toDto(petType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPetTypeMockMvc.perform(put("/api/pet-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(petTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PetType in the database
        List<PetType> petTypeList = petTypeRepository.findAll();
        assertThat(petTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePetType() throws Exception {
        // Initialize the database
        petTypeRepository.saveAndFlush(petType);

        int databaseSizeBeforeDelete = petTypeRepository.findAll().size();

        // Delete the petType
        restPetTypeMockMvc.perform(delete("/api/pet-types/{id}", petType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PetType> petTypeList = petTypeRepository.findAll();
        assertThat(petTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
