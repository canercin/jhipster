package com.jhipster.demo.web.rest;

import static com.jhipster.demo.domain.PeopleAsserts.*;
import static com.jhipster.demo.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jhipster.demo.IntegrationTest;
import com.jhipster.demo.domain.People;
import com.jhipster.demo.repository.PeopleRepository;
import com.jhipster.demo.service.dto.PeopleDTO;
import com.jhipster.demo.service.mapper.PeopleMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PeopleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PeopleResourceIT {

    private static final String DEFAULT_FIRSTNAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRSTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_LASTNAME = "AAAAAAAAAA";
    private static final String UPDATED_LASTNAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/people";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PeopleRepository peopleRepository;

    @Autowired
    private PeopleMapper peopleMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPeopleMockMvc;

    private People people;

    private People insertedPeople;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static People createEntity() {
        return new People().firstname(DEFAULT_FIRSTNAME).lastname(DEFAULT_LASTNAME);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static People createUpdatedEntity() {
        return new People().firstname(UPDATED_FIRSTNAME).lastname(UPDATED_LASTNAME);
    }

    @BeforeEach
    public void initTest() {
        people = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedPeople != null) {
            peopleRepository.delete(insertedPeople);
            insertedPeople = null;
        }
    }

    @Test
    @Transactional
    void createPeople() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the People
        PeopleDTO peopleDTO = peopleMapper.toDto(people);
        var returnedPeopleDTO = om.readValue(
            restPeopleMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(peopleDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PeopleDTO.class
        );

        // Validate the People in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPeople = peopleMapper.toEntity(returnedPeopleDTO);
        assertPeopleUpdatableFieldsEquals(returnedPeople, getPersistedPeople(returnedPeople));

        insertedPeople = returnedPeople;
    }

    @Test
    @Transactional
    void createPeopleWithExistingId() throws Exception {
        // Create the People with an existing ID
        people.setId(1L);
        PeopleDTO peopleDTO = peopleMapper.toDto(people);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPeopleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(peopleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the People in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPeople() throws Exception {
        // Initialize the database
        insertedPeople = peopleRepository.saveAndFlush(people);

        // Get all the peopleList
        restPeopleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(people.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstname").value(hasItem(DEFAULT_FIRSTNAME)))
            .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME)));
    }

    @Test
    @Transactional
    void getPeople() throws Exception {
        // Initialize the database
        insertedPeople = peopleRepository.saveAndFlush(people);

        // Get the people
        restPeopleMockMvc
            .perform(get(ENTITY_API_URL_ID, people.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(people.getId().intValue()))
            .andExpect(jsonPath("$.firstname").value(DEFAULT_FIRSTNAME))
            .andExpect(jsonPath("$.lastname").value(DEFAULT_LASTNAME));
    }

    @Test
    @Transactional
    void getNonExistingPeople() throws Exception {
        // Get the people
        restPeopleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPeople() throws Exception {
        // Initialize the database
        insertedPeople = peopleRepository.saveAndFlush(people);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the people
        People updatedPeople = peopleRepository.findById(people.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPeople are not directly saved in db
        em.detach(updatedPeople);
        updatedPeople.firstname(UPDATED_FIRSTNAME).lastname(UPDATED_LASTNAME);
        PeopleDTO peopleDTO = peopleMapper.toDto(updatedPeople);

        restPeopleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, peopleDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(peopleDTO))
            )
            .andExpect(status().isOk());

        // Validate the People in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPeopleToMatchAllProperties(updatedPeople);
    }

    @Test
    @Transactional
    void putNonExistingPeople() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        people.setId(longCount.incrementAndGet());

        // Create the People
        PeopleDTO peopleDTO = peopleMapper.toDto(people);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPeopleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, peopleDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(peopleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the People in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPeople() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        people.setId(longCount.incrementAndGet());

        // Create the People
        PeopleDTO peopleDTO = peopleMapper.toDto(people);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPeopleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(peopleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the People in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPeople() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        people.setId(longCount.incrementAndGet());

        // Create the People
        PeopleDTO peopleDTO = peopleMapper.toDto(people);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPeopleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(peopleDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the People in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePeopleWithPatch() throws Exception {
        // Initialize the database
        insertedPeople = peopleRepository.saveAndFlush(people);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the people using partial update
        People partialUpdatedPeople = new People();
        partialUpdatedPeople.setId(people.getId());

        partialUpdatedPeople.firstname(UPDATED_FIRSTNAME).lastname(UPDATED_LASTNAME);

        restPeopleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPeople.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPeople))
            )
            .andExpect(status().isOk());

        // Validate the People in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPeopleUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedPeople, people), getPersistedPeople(people));
    }

    @Test
    @Transactional
    void fullUpdatePeopleWithPatch() throws Exception {
        // Initialize the database
        insertedPeople = peopleRepository.saveAndFlush(people);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the people using partial update
        People partialUpdatedPeople = new People();
        partialUpdatedPeople.setId(people.getId());

        partialUpdatedPeople.firstname(UPDATED_FIRSTNAME).lastname(UPDATED_LASTNAME);

        restPeopleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPeople.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPeople))
            )
            .andExpect(status().isOk());

        // Validate the People in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPeopleUpdatableFieldsEquals(partialUpdatedPeople, getPersistedPeople(partialUpdatedPeople));
    }

    @Test
    @Transactional
    void patchNonExistingPeople() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        people.setId(longCount.incrementAndGet());

        // Create the People
        PeopleDTO peopleDTO = peopleMapper.toDto(people);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPeopleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, peopleDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(peopleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the People in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPeople() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        people.setId(longCount.incrementAndGet());

        // Create the People
        PeopleDTO peopleDTO = peopleMapper.toDto(people);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPeopleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(peopleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the People in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPeople() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        people.setId(longCount.incrementAndGet());

        // Create the People
        PeopleDTO peopleDTO = peopleMapper.toDto(people);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPeopleMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(peopleDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the People in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePeople() throws Exception {
        // Initialize the database
        insertedPeople = peopleRepository.saveAndFlush(people);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the people
        restPeopleMockMvc
            .perform(delete(ENTITY_API_URL_ID, people.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return peopleRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected People getPersistedPeople(People people) {
        return peopleRepository.findById(people.getId()).orElseThrow();
    }

    protected void assertPersistedPeopleToMatchAllProperties(People expectedPeople) {
        assertPeopleAllPropertiesEquals(expectedPeople, getPersistedPeople(expectedPeople));
    }

    protected void assertPersistedPeopleToMatchUpdatableProperties(People expectedPeople) {
        assertPeopleAllUpdatablePropertiesEquals(expectedPeople, getPersistedPeople(expectedPeople));
    }
}
