package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestEnumApp;
import com.mycompany.myapp.domain.PersonDetails;
import com.mycompany.myapp.repository.PersonDetailsRepository;

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
 * Integration tests for the {@link PersonDetailsResource} REST controller.
 */
@SpringBootTest(classes = TestEnumApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PersonDetailsResourceIT {

    private static final String DEFAULT_NID = "AAAAAAAAAA";
    private static final String UPDATED_NID = "BBBBBBBBBB";

    private static final String DEFAULT_DOB = "AAAAAAAAAA";
    private static final String UPDATED_DOB = "BBBBBBBBBB";

    @Autowired
    private PersonDetailsRepository personDetailsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPersonDetailsMockMvc;

    private PersonDetails personDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonDetails createEntity(EntityManager em) {
        PersonDetails personDetails = new PersonDetails()
            .nid(DEFAULT_NID)
            .dob(DEFAULT_DOB);
        return personDetails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonDetails createUpdatedEntity(EntityManager em) {
        PersonDetails personDetails = new PersonDetails()
            .nid(UPDATED_NID)
            .dob(UPDATED_DOB);
        return personDetails;
    }

    @BeforeEach
    public void initTest() {
        personDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonDetails() throws Exception {
        int databaseSizeBeforeCreate = personDetailsRepository.findAll().size();
        // Create the PersonDetails
        restPersonDetailsMockMvc.perform(post("/api/person-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personDetails)))
            .andExpect(status().isCreated());

        // Validate the PersonDetails in the database
        List<PersonDetails> personDetailsList = personDetailsRepository.findAll();
        assertThat(personDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        PersonDetails testPersonDetails = personDetailsList.get(personDetailsList.size() - 1);
        assertThat(testPersonDetails.getNid()).isEqualTo(DEFAULT_NID);
        assertThat(testPersonDetails.getDob()).isEqualTo(DEFAULT_DOB);
    }

    @Test
    @Transactional
    public void createPersonDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personDetailsRepository.findAll().size();

        // Create the PersonDetails with an existing ID
        personDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonDetailsMockMvc.perform(post("/api/person-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personDetails)))
            .andExpect(status().isBadRequest());

        // Validate the PersonDetails in the database
        List<PersonDetails> personDetailsList = personDetailsRepository.findAll();
        assertThat(personDetailsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNidIsRequired() throws Exception {
        int databaseSizeBeforeTest = personDetailsRepository.findAll().size();
        // set the field null
        personDetails.setNid(null);

        // Create the PersonDetails, which fails.


        restPersonDetailsMockMvc.perform(post("/api/person-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personDetails)))
            .andExpect(status().isBadRequest());

        List<PersonDetails> personDetailsList = personDetailsRepository.findAll();
        assertThat(personDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDobIsRequired() throws Exception {
        int databaseSizeBeforeTest = personDetailsRepository.findAll().size();
        // set the field null
        personDetails.setDob(null);

        // Create the PersonDetails, which fails.


        restPersonDetailsMockMvc.perform(post("/api/person-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personDetails)))
            .andExpect(status().isBadRequest());

        List<PersonDetails> personDetailsList = personDetailsRepository.findAll();
        assertThat(personDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPersonDetails() throws Exception {
        // Initialize the database
        personDetailsRepository.saveAndFlush(personDetails);

        // Get all the personDetailsList
        restPersonDetailsMockMvc.perform(get("/api/person-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].nid").value(hasItem(DEFAULT_NID)))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB)));
    }
    
    @Test
    @Transactional
    public void getPersonDetails() throws Exception {
        // Initialize the database
        personDetailsRepository.saveAndFlush(personDetails);

        // Get the personDetails
        restPersonDetailsMockMvc.perform(get("/api/person-details/{id}", personDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(personDetails.getId().intValue()))
            .andExpect(jsonPath("$.nid").value(DEFAULT_NID))
            .andExpect(jsonPath("$.dob").value(DEFAULT_DOB));
    }
    @Test
    @Transactional
    public void getNonExistingPersonDetails() throws Exception {
        // Get the personDetails
        restPersonDetailsMockMvc.perform(get("/api/person-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonDetails() throws Exception {
        // Initialize the database
        personDetailsRepository.saveAndFlush(personDetails);

        int databaseSizeBeforeUpdate = personDetailsRepository.findAll().size();

        // Update the personDetails
        PersonDetails updatedPersonDetails = personDetailsRepository.findById(personDetails.getId()).get();
        // Disconnect from session so that the updates on updatedPersonDetails are not directly saved in db
        em.detach(updatedPersonDetails);
        updatedPersonDetails
            .nid(UPDATED_NID)
            .dob(UPDATED_DOB);

        restPersonDetailsMockMvc.perform(put("/api/person-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonDetails)))
            .andExpect(status().isOk());

        // Validate the PersonDetails in the database
        List<PersonDetails> personDetailsList = personDetailsRepository.findAll();
        assertThat(personDetailsList).hasSize(databaseSizeBeforeUpdate);
        PersonDetails testPersonDetails = personDetailsList.get(personDetailsList.size() - 1);
        assertThat(testPersonDetails.getNid()).isEqualTo(UPDATED_NID);
        assertThat(testPersonDetails.getDob()).isEqualTo(UPDATED_DOB);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonDetails() throws Exception {
        int databaseSizeBeforeUpdate = personDetailsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonDetailsMockMvc.perform(put("/api/person-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personDetails)))
            .andExpect(status().isBadRequest());

        // Validate the PersonDetails in the database
        List<PersonDetails> personDetailsList = personDetailsRepository.findAll();
        assertThat(personDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePersonDetails() throws Exception {
        // Initialize the database
        personDetailsRepository.saveAndFlush(personDetails);

        int databaseSizeBeforeDelete = personDetailsRepository.findAll().size();

        // Delete the personDetails
        restPersonDetailsMockMvc.perform(delete("/api/person-details/{id}", personDetails.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PersonDetails> personDetailsList = personDetailsRepository.findAll();
        assertThat(personDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
