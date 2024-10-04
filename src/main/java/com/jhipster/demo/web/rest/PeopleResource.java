package com.jhipster.demo.web.rest;

import com.jhipster.demo.repository.PeopleRepository;
import com.jhipster.demo.service.PeopleService;
import com.jhipster.demo.service.dto.PeopleDTO;
import com.jhipster.demo.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.jhipster.demo.domain.People}.
 */
@RestController
@RequestMapping("/api/people")
public class PeopleResource {

    private static final Logger LOG = LoggerFactory.getLogger(PeopleResource.class);

    private static final String ENTITY_NAME = "people";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PeopleService peopleService;

    private final PeopleRepository peopleRepository;

    public PeopleResource(PeopleService peopleService, PeopleRepository peopleRepository) {
        this.peopleService = peopleService;
        this.peopleRepository = peopleRepository;
    }

    /**
     * {@code POST  /people} : Create a new people.
     *
     * @param peopleDTO the peopleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new peopleDTO, or with status {@code 400 (Bad Request)} if the people has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PeopleDTO> createPeople(@RequestBody PeopleDTO peopleDTO) throws URISyntaxException {
        LOG.debug("REST request to save People : {}", peopleDTO);
        if (peopleDTO.getId() != null) {
            throw new BadRequestAlertException("A new people cannot already have an ID", ENTITY_NAME, "idexists");
        }
        peopleDTO = peopleService.save(peopleDTO);
        return ResponseEntity.created(new URI("/api/people/" + peopleDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, peopleDTO.getId().toString()))
            .body(peopleDTO);
    }

    /**
     * {@code PUT  /people/:id} : Updates an existing people.
     *
     * @param id the id of the peopleDTO to save.
     * @param peopleDTO the peopleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated peopleDTO,
     * or with status {@code 400 (Bad Request)} if the peopleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the peopleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PeopleDTO> updatePeople(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PeopleDTO peopleDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update People : {}, {}", id, peopleDTO);
        if (peopleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, peopleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!peopleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        peopleDTO = peopleService.update(peopleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, peopleDTO.getId().toString()))
            .body(peopleDTO);
    }

    /**
     * {@code PATCH  /people/:id} : Partial updates given fields of an existing people, field will ignore if it is null
     *
     * @param id the id of the peopleDTO to save.
     * @param peopleDTO the peopleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated peopleDTO,
     * or with status {@code 400 (Bad Request)} if the peopleDTO is not valid,
     * or with status {@code 404 (Not Found)} if the peopleDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the peopleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PeopleDTO> partialUpdatePeople(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PeopleDTO peopleDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update People partially : {}, {}", id, peopleDTO);
        if (peopleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, peopleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!peopleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PeopleDTO> result = peopleService.partialUpdate(peopleDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, peopleDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /people} : get all the people.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of people in body.
     */
    @GetMapping("")
    public List<PeopleDTO> getAllPeople() {
        LOG.debug("REST request to get all People");
        return peopleService.findAll();
    }

    /**
     * {@code GET  /people/:id} : get the "id" people.
     *
     * @param id the id of the peopleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the peopleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PeopleDTO> getPeople(@PathVariable("id") Long id) {
        LOG.debug("REST request to get People : {}", id);
        Optional<PeopleDTO> peopleDTO = peopleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(peopleDTO);
    }

    /**
     * {@code DELETE  /people/:id} : delete the "id" people.
     *
     * @param id the id of the peopleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePeople(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete People : {}", id);
        peopleService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
