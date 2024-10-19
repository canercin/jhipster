package com.jhipster.demo.web.rest;

import com.jhipster.demo.domain.Warehouse;
import com.jhipster.demo.repository.WarehouseRepository;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.jhipster.demo.domain.Warehouse}.
 */
@RestController
@RequestMapping("/api/warehouses")
@Transactional
public class WarehouseResource {

    private static final Logger LOG = LoggerFactory.getLogger(WarehouseResource.class);

    private static final String ENTITY_NAME = "warehouse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WarehouseRepository warehouseRepository;

    public WarehouseResource(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    /**
     * {@code POST  /warehouses} : Create a new warehouse.
     *
     * @param warehouse the warehouse to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new warehouse, or with status {@code 400 (Bad Request)} if the warehouse has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Warehouse> createWarehouse(@RequestBody Warehouse warehouse) throws URISyntaxException {
        LOG.debug("REST request to save Warehouse : {}", warehouse);
        if (warehouse.getId() != null) {
            throw new BadRequestAlertException("A new warehouse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        warehouse = warehouseRepository.save(warehouse);
        return ResponseEntity.created(new URI("/api/warehouses/" + warehouse.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, warehouse.getId().toString()))
            .body(warehouse);
    }

    /**
     * {@code PUT  /warehouses/:id} : Updates an existing warehouse.
     *
     * @param id the id of the warehouse to save.
     * @param warehouse the warehouse to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated warehouse,
     * or with status {@code 400 (Bad Request)} if the warehouse is not valid,
     * or with status {@code 500 (Internal Server Error)} if the warehouse couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Warehouse> updateWarehouse(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Warehouse warehouse
    ) throws URISyntaxException {
        LOG.debug("REST request to update Warehouse : {}, {}", id, warehouse);
        if (warehouse.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, warehouse.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!warehouseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        warehouse = warehouseRepository.save(warehouse);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, warehouse.getId().toString()))
            .body(warehouse);
    }

    /**
     * {@code PATCH  /warehouses/:id} : Partial updates given fields of an existing warehouse, field will ignore if it is null
     *
     * @param id the id of the warehouse to save.
     * @param warehouse the warehouse to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated warehouse,
     * or with status {@code 400 (Bad Request)} if the warehouse is not valid,
     * or with status {@code 404 (Not Found)} if the warehouse is not found,
     * or with status {@code 500 (Internal Server Error)} if the warehouse couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Warehouse> partialUpdateWarehouse(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Warehouse warehouse
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Warehouse partially : {}, {}", id, warehouse);
        if (warehouse.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, warehouse.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!warehouseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Warehouse> result = warehouseRepository
            .findById(warehouse.getId())
            .map(existingWarehouse -> {
                if (warehouse.getName() != null) {
                    existingWarehouse.setName(warehouse.getName());
                }
                if (warehouse.getAddress() != null) {
                    existingWarehouse.setAddress(warehouse.getAddress());
                }

                return existingWarehouse;
            })
            .map(warehouseRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, warehouse.getId().toString())
        );
    }

    /**
     * {@code GET  /warehouses} : get all the warehouses.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of warehouses in body.
     */
    @GetMapping("")
    public List<Warehouse> getAllWarehouses(@RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload) {
        LOG.debug("REST request to get all Warehouses");
        if (eagerload) {
            return warehouseRepository.findAllWithEagerRelationships();
        } else {
            return warehouseRepository.findAll();
        }
    }

    /**
     * {@code GET  /warehouses/:id} : get the "id" warehouse.
     *
     * @param id the id of the warehouse to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the warehouse, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Warehouse> getWarehouse(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Warehouse : {}", id);
        Optional<Warehouse> warehouse = warehouseRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(warehouse);
    }

    /**
     * {@code DELETE  /warehouses/:id} : delete the "id" warehouse.
     *
     * @param id the id of the warehouse to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWarehouse(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Warehouse : {}", id);
        warehouseRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
