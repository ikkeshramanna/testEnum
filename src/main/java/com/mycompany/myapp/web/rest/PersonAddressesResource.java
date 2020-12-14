package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.PersonAddresses;
import com.mycompany.myapp.repository.PersonAddressesRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.PersonAddresses}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PersonAddressesResource {

    private final Logger log = LoggerFactory.getLogger(PersonAddressesResource.class);

    private static final String ENTITY_NAME = "personAddresses";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonAddressesRepository personAddressesRepository;

    public PersonAddressesResource(PersonAddressesRepository personAddressesRepository) {
        this.personAddressesRepository = personAddressesRepository;
    }

    /**
     * {@code POST  /person-addresses} : Create a new personAddresses.
     *
     * @param personAddresses the personAddresses to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new personAddresses, or with status {@code 400 (Bad Request)} if the personAddresses has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/person-addresses")
    public ResponseEntity<PersonAddresses> createPersonAddresses(@Valid @RequestBody PersonAddresses personAddresses) throws URISyntaxException {
        log.debug("REST request to save PersonAddresses : {}", personAddresses);
        if (personAddresses.getId() != null) {
            throw new BadRequestAlertException("A new personAddresses cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonAddresses result = personAddressesRepository.save(personAddresses);
        return ResponseEntity.created(new URI("/api/person-addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /person-addresses} : Updates an existing personAddresses.
     *
     * @param personAddresses the personAddresses to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personAddresses,
     * or with status {@code 400 (Bad Request)} if the personAddresses is not valid,
     * or with status {@code 500 (Internal Server Error)} if the personAddresses couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/person-addresses")
    public ResponseEntity<PersonAddresses> updatePersonAddresses(@Valid @RequestBody PersonAddresses personAddresses) throws URISyntaxException {
        log.debug("REST request to update PersonAddresses : {}", personAddresses);
        if (personAddresses.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PersonAddresses result = personAddressesRepository.save(personAddresses);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, personAddresses.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /person-addresses} : get all the personAddresses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of personAddresses in body.
     */
    @GetMapping("/person-addresses")
    public List<PersonAddresses> getAllPersonAddresses() {
        log.debug("REST request to get all PersonAddresses");
        return personAddressesRepository.findAll();
    }

    /**
     * {@code GET  /person-addresses/:id} : get the "id" personAddresses.
     *
     * @param id the id of the personAddresses to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the personAddresses, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/person-addresses/{id}")
    public ResponseEntity<PersonAddresses> getPersonAddresses(@PathVariable Long id) {
        log.debug("REST request to get PersonAddresses : {}", id);
        Optional<PersonAddresses> personAddresses = personAddressesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(personAddresses);
    }

    /**
     * {@code DELETE  /person-addresses/:id} : delete the "id" personAddresses.
     *
     * @param id the id of the personAddresses to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/person-addresses/{id}")
    public ResponseEntity<Void> deletePersonAddresses(@PathVariable Long id) {
        log.debug("REST request to delete PersonAddresses : {}", id);
        personAddressesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
