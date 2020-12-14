package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestEnumApp;
import com.mycompany.myapp.domain.PersonAddresses;
import com.mycompany.myapp.repository.PersonAddressesRepository;

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

import com.mycompany.myapp.domain.enumeration.TestType;
/**
 * Integration tests for the {@link PersonAddressesResource} REST controller.
 */
@SpringBootTest(classes = TestEnumApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PersonAddressesResourceIT {

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Integer DEFAULT_ZIPCODE = 1;
    private static final Integer UPDATED_ZIPCODE = 2;

    private static final TestType DEFAULT_TEST = TestType.AT_SUPPLEMENTARY_FEEDERS;
    private static final TestType UPDATED_TEST = TestType.OPPORTUNISTIC_AT_NEST;

    @Autowired
    private PersonAddressesRepository personAddressesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPersonAddressesMockMvc;

    private PersonAddresses personAddresses;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonAddresses createEntity(EntityManager em) {
        PersonAddresses personAddresses = new PersonAddresses()
            .address(DEFAULT_ADDRESS)
            .zipcode(DEFAULT_ZIPCODE)
            .test(DEFAULT_TEST);
        return personAddresses;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonAddresses createUpdatedEntity(EntityManager em) {
        PersonAddresses personAddresses = new PersonAddresses()
            .address(UPDATED_ADDRESS)
            .zipcode(UPDATED_ZIPCODE)
            .test(UPDATED_TEST);
        return personAddresses;
    }

    @BeforeEach
    public void initTest() {
        personAddresses = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonAddresses() throws Exception {
        int databaseSizeBeforeCreate = personAddressesRepository.findAll().size();
        // Create the PersonAddresses
        restPersonAddressesMockMvc.perform(post("/api/person-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personAddresses)))
            .andExpect(status().isCreated());

        // Validate the PersonAddresses in the database
        List<PersonAddresses> personAddressesList = personAddressesRepository.findAll();
        assertThat(personAddressesList).hasSize(databaseSizeBeforeCreate + 1);
        PersonAddresses testPersonAddresses = personAddressesList.get(personAddressesList.size() - 1);
        assertThat(testPersonAddresses.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testPersonAddresses.getZipcode()).isEqualTo(DEFAULT_ZIPCODE);
        assertThat(testPersonAddresses.getTest()).isEqualTo(DEFAULT_TEST);
    }

    @Test
    @Transactional
    public void createPersonAddressesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personAddressesRepository.findAll().size();

        // Create the PersonAddresses with an existing ID
        personAddresses.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonAddressesMockMvc.perform(post("/api/person-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personAddresses)))
            .andExpect(status().isBadRequest());

        // Validate the PersonAddresses in the database
        List<PersonAddresses> personAddressesList = personAddressesRepository.findAll();
        assertThat(personAddressesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = personAddressesRepository.findAll().size();
        // set the field null
        personAddresses.setAddress(null);

        // Create the PersonAddresses, which fails.


        restPersonAddressesMockMvc.perform(post("/api/person-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personAddresses)))
            .andExpect(status().isBadRequest());

        List<PersonAddresses> personAddressesList = personAddressesRepository.findAll();
        assertThat(personAddressesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPersonAddresses() throws Exception {
        // Initialize the database
        personAddressesRepository.saveAndFlush(personAddresses);

        // Get all the personAddressesList
        restPersonAddressesMockMvc.perform(get("/api/person-addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personAddresses.getId().intValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].zipcode").value(hasItem(DEFAULT_ZIPCODE)))
            .andExpect(jsonPath("$.[*].test").value(hasItem(DEFAULT_TEST.toString())));
    }
    
    @Test
    @Transactional
    public void getPersonAddresses() throws Exception {
        // Initialize the database
        personAddressesRepository.saveAndFlush(personAddresses);

        // Get the personAddresses
        restPersonAddressesMockMvc.perform(get("/api/person-addresses/{id}", personAddresses.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(personAddresses.getId().intValue()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.zipcode").value(DEFAULT_ZIPCODE))
            .andExpect(jsonPath("$.test").value(DEFAULT_TEST.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPersonAddresses() throws Exception {
        // Get the personAddresses
        restPersonAddressesMockMvc.perform(get("/api/person-addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonAddresses() throws Exception {
        // Initialize the database
        personAddressesRepository.saveAndFlush(personAddresses);

        int databaseSizeBeforeUpdate = personAddressesRepository.findAll().size();

        // Update the personAddresses
        PersonAddresses updatedPersonAddresses = personAddressesRepository.findById(personAddresses.getId()).get();
        // Disconnect from session so that the updates on updatedPersonAddresses are not directly saved in db
        em.detach(updatedPersonAddresses);
        updatedPersonAddresses
            .address(UPDATED_ADDRESS)
            .zipcode(UPDATED_ZIPCODE)
            .test(UPDATED_TEST);

        restPersonAddressesMockMvc.perform(put("/api/person-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonAddresses)))
            .andExpect(status().isOk());

        // Validate the PersonAddresses in the database
        List<PersonAddresses> personAddressesList = personAddressesRepository.findAll();
        assertThat(personAddressesList).hasSize(databaseSizeBeforeUpdate);
        PersonAddresses testPersonAddresses = personAddressesList.get(personAddressesList.size() - 1);
        assertThat(testPersonAddresses.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testPersonAddresses.getZipcode()).isEqualTo(UPDATED_ZIPCODE);
        assertThat(testPersonAddresses.getTest()).isEqualTo(UPDATED_TEST);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonAddresses() throws Exception {
        int databaseSizeBeforeUpdate = personAddressesRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonAddressesMockMvc.perform(put("/api/person-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personAddresses)))
            .andExpect(status().isBadRequest());

        // Validate the PersonAddresses in the database
        List<PersonAddresses> personAddressesList = personAddressesRepository.findAll();
        assertThat(personAddressesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePersonAddresses() throws Exception {
        // Initialize the database
        personAddressesRepository.saveAndFlush(personAddresses);

        int databaseSizeBeforeDelete = personAddressesRepository.findAll().size();

        // Delete the personAddresses
        restPersonAddressesMockMvc.perform(delete("/api/person-addresses/{id}", personAddresses.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PersonAddresses> personAddressesList = personAddressesRepository.findAll();
        assertThat(personAddressesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
