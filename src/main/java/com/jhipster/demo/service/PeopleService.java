package com.jhipster.demo.service;

import com.jhipster.demo.domain.People;
import com.jhipster.demo.repository.PeopleRepository;
import com.jhipster.demo.service.dto.PeopleDTO;
import com.jhipster.demo.service.mapper.PeopleMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.jhipster.demo.domain.People}.
 */
@Service
@Transactional
public class PeopleService {

    private static final Logger LOG = LoggerFactory.getLogger(PeopleService.class);

    private final PeopleRepository peopleRepository;

    private final PeopleMapper peopleMapper;

    public PeopleService(PeopleRepository peopleRepository, PeopleMapper peopleMapper) {
        this.peopleRepository = peopleRepository;
        this.peopleMapper = peopleMapper;
    }

    /**
     * Save a people.
     *
     * @param peopleDTO the entity to save.
     * @return the persisted entity.
     */
    public PeopleDTO save(PeopleDTO peopleDTO) {
        LOG.debug("Request to save People : {}", peopleDTO);
        People people = peopleMapper.toEntity(peopleDTO);
        people = peopleRepository.save(people);
        return peopleMapper.toDto(people);
    }

    /**
     * Update a people.
     *
     * @param peopleDTO the entity to save.
     * @return the persisted entity.
     */
    public PeopleDTO update(PeopleDTO peopleDTO) {
        LOG.debug("Request to update People : {}", peopleDTO);
        People people = peopleMapper.toEntity(peopleDTO);
        people = peopleRepository.save(people);
        return peopleMapper.toDto(people);
    }

    /**
     * Partially update a people.
     *
     * @param peopleDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PeopleDTO> partialUpdate(PeopleDTO peopleDTO) {
        LOG.debug("Request to partially update People : {}", peopleDTO);

        return peopleRepository
            .findById(peopleDTO.getId())
            .map(existingPeople -> {
                peopleMapper.partialUpdate(existingPeople, peopleDTO);

                return existingPeople;
            })
            .map(peopleRepository::save)
            .map(peopleMapper::toDto);
    }

    /**
     * Get all the people.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PeopleDTO> findAll() {
        LOG.debug("Request to get all People");
        return peopleRepository.findAll().stream().map(peopleMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one people by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PeopleDTO> findOne(Long id) {
        LOG.debug("Request to get People : {}", id);
        return peopleRepository.findById(id).map(peopleMapper::toDto);
    }

    /**
     * Delete the people by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete People : {}", id);
        peopleRepository.deleteById(id);
    }
}
