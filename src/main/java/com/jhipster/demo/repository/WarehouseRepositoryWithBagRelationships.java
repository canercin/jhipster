package com.jhipster.demo.repository;

import com.jhipster.demo.domain.Warehouse;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface WarehouseRepositoryWithBagRelationships {
    Optional<Warehouse> fetchBagRelationships(Optional<Warehouse> warehouse);

    List<Warehouse> fetchBagRelationships(List<Warehouse> warehouses);

    Page<Warehouse> fetchBagRelationships(Page<Warehouse> warehouses);
}
