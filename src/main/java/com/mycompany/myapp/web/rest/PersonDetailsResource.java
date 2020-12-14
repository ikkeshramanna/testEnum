package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.PersonDetails;
import com.mycompany.myapp.repository.PersonDetailsRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.PersonDetails}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PersonDetailsResource {

    private final Logger log = LoggerFactory.getLogger(PersonDetailsResource.class);

    private static final String ENTITY_NAME = "personDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonDetailsRepository personDetailsRepository;

    public PersonDetailsResource(PersonDetailsRepository personDetailsRepository) {
        this.personDetailsRepository = personDetailsRepository;
    }

    /**
     * {@code POST  /person-details} : Create a new personDetails.
     *
     * @param personDetails the personDetails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new personDetails, or with status {@code 400 (Bad Request)} if the personDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/person-details")
    public ResponseEntity<PersonDetails> createPersonDetails(@Valid @RequestBody PersonDetails personDetails) throws URISyntaxException {
        log.debug("REST request to save PersonDetails : {}", personDetails);
        if (personDetails.getId() != null) {
            throw new BadRequestAlertException("A new personDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonDetails result = personDetailsRepository.save(personDetails);
        return ResponseEntity.created(new URI("/api/person-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /person-details} : Updates an existing personDetails.
     *
     * @param personDetails the personDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personDetails,
     * or with status {@code 400 (Bad Request)} if the personDetails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the personDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/person-details")
    public ResponseEntity<PersonDetails> updatePersonDetails(@Valid @RequestBody PersonDetails personDetails) throws URISyntaxException {
        log.debug("REST request to update PersonDetails : {}", personDetails);
        if (personDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PersonDetails result = personDetailsRepository.save(personDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, personDetails.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /person-details} : get all the personDetails.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of personDetails in body.
     */
    @GetMapping("/person-details")
    public List<PersonDetails> getAllPersonDetails() {
        log.debug("REST request to get all PersonDetails");
        return personDetailsRepository.findAll();
    }

    /**
     * {@code GET  /person-details/:id} : get the "id" personDetails.
     *
     * @param id the id of the personDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the personDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/person-details/{id}")
    public ResponseEntity<PersonDetails> getPersonDetails(@PathVariable Long id) {
        log.debug("REST request to get PersonDetails : {}", id);
        Optional<PersonDetails> personDetails = personDetailsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(personDetails);
    }

    /**
     * {@code DELETE  /person-details/:id} : delete the "id" personDetails.
     *
     * @param id the id of the personDetails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/person-details/{id}")
    public ResponseEntity<Void> deletePersonDetails(@PathVariable Long id) {
        log.debug("REST request to delete PersonDetails : {}", id);
        personDetailsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
