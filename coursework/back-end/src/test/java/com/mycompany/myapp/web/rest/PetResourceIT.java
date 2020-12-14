package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.CourseworkApp;
import com.mycompany.myapp.domain.Pet;
import com.mycompany.myapp.repository.PetRepository;
import com.mycompany.myapp.repository.search.PetSearchRepository;
import com.mycompany.myapp.service.PetService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PetResource} REST controller.
 */
@SpringBootTest(classes = CourseworkApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PetResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private PetService petService;

    /**
     * This repository is mocked in the com.mycompany.myapp.repository.search test package.
     *
     * @see com.mycompany.myapp.repository.search.PetSearchRepositoryMockConfiguration
     */
    @Autowired
    private PetSearchRepository mockPetSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPetMockMvc;

    private Pet pet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pet createEntity(EntityManager em) {
        Pet pet = new Pet()
            .name(DEFAULT_NAME);
        return pet;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pet createUpdatedEntity(EntityManager em) {
        Pet pet = new Pet()
            .name(UPDATED_NAME);
        return pet;
    }

    @BeforeEach
    public void initTest() {
        pet = createEntity(em);
    }

    @Test
    @Transactional
    public void createPet() throws Exception {
        int databaseSizeBeforeCreate = petRepository.findAll().size();
        // Create the Pet
        restPetMockMvc.perform(post("/api/pets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pet)))
            .andExpect(status().isCreated());

        // Validate the Pet in the database
        List<Pet> petList = petRepository.findAll();
        assertThat(petList).hasSize(databaseSizeBeforeCreate + 1);
        Pet testPet = petList.get(petList.size() - 1);
        assertThat(testPet.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the Pet in Elasticsearch
        verify(mockPetSearchRepository, times(1)).save(testPet);
    }

    @Test
    @Transactional
    public void createPetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = petRepository.findAll().size();

        // Create the Pet with an existing ID
        pet.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPetMockMvc.perform(post("/api/pets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pet)))
            .andExpect(status().isBadRequest());

        // Validate the Pet in the database
        List<Pet> petList = petRepository.findAll();
        assertThat(petList).hasSize(databaseSizeBeforeCreate);

        // Validate the Pet in Elasticsearch
        verify(mockPetSearchRepository, times(0)).save(pet);
    }


    @Test
    @Transactional
    public void getAllPets() throws Exception {
        // Initialize the database
        petRepository.saveAndFlush(pet);

        // Get all the petList
        restPetMockMvc.perform(get("/api/pets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pet.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getPet() throws Exception {
        // Initialize the database
        petRepository.saveAndFlush(pet);

        // Get the pet
        restPetMockMvc.perform(get("/api/pets/{id}", pet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pet.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingPet() throws Exception {
        // Get the pet
        restPetMockMvc.perform(get("/api/pets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePet() throws Exception {
        // Initialize the database
        petService.save(pet);

        int databaseSizeBeforeUpdate = petRepository.findAll().size();

        // Update the pet
        Pet updatedPet = petRepository.findById(pet.getId()).get();
        // Disconnect from session so that the updates on updatedPet are not directly saved in db
        em.detach(updatedPet);
        updatedPet
            .name(UPDATED_NAME);

        restPetMockMvc.perform(put("/api/pets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPet)))
            .andExpect(status().isOk());

        // Validate the Pet in the database
        List<Pet> petList = petRepository.findAll();
        assertThat(petList).hasSize(databaseSizeBeforeUpdate);
        Pet testPet = petList.get(petList.size() - 1);
        assertThat(testPet.getName()).isEqualTo(UPDATED_NAME);

        // Validate the Pet in Elasticsearch
        verify(mockPetSearchRepository, times(2)).save(testPet);
    }

    @Test
    @Transactional
    public void updateNonExistingPet() throws Exception {
        int databaseSizeBeforeUpdate = petRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPetMockMvc.perform(put("/api/pets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pet)))
            .andExpect(status().isBadRequest());

        // Validate the Pet in the database
        List<Pet> petList = petRepository.findAll();
        assertThat(petList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Pet in Elasticsearch
        verify(mockPetSearchRepository, times(0)).save(pet);
    }

    @Test
    @Transactional
    public void deletePet() throws Exception {
        // Initialize the database
        petService.save(pet);

        int databaseSizeBeforeDelete = petRepository.findAll().size();

        // Delete the pet
        restPetMockMvc.perform(delete("/api/pets/{id}", pet.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pet> petList = petRepository.findAll();
        assertThat(petList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Pet in Elasticsearch
        verify(mockPetSearchRepository, times(1)).deleteById(pet.getId());
    }

    @Test
    @Transactional
    public void searchPet() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        petService.save(pet);
        when(mockPetSearchRepository.search(queryStringQuery("id:" + pet.getId())))
            .thenReturn(Collections.singletonList(pet));

        // Search the pet
        restPetMockMvc.perform(get("/api/_search/pets?query=id:" + pet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pet.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
}
