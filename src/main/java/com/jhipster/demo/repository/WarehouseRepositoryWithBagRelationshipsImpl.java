package com.jhipster.demo.repository;

import com.jhipster.demo.domain.Warehouse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class WarehouseRepositoryWithBagRelationshipsImpl implements WarehouseRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String WAREHOUSES_PARAMETER = "warehouses";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Warehouse> fetchBagRelationships(Optional<Warehouse> warehouse) {
        return warehouse.map(this::fetchProducts);
    }

    @Override
    public Page<Warehouse> fetchBagRelationships(Page<Warehouse> warehouses) {
        return new PageImpl<>(fetchBagRelationships(warehouses.getContent()), warehouses.getPageable(), warehouses.getTotalElements());
    }

    @Override
    public List<Warehouse> fetchBagRelationships(List<Warehouse> warehouses) {
        return Optional.of(warehouses).map(this::fetchProducts).orElse(Collections.emptyList());
    }

    Warehouse fetchProducts(Warehouse result) {
        return entityManager
            .createQuery(
                "select warehouse from Warehouse warehouse left join fetch warehouse.products where warehouse.id = :id",
                Warehouse.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Warehouse> fetchProducts(List<Warehouse> warehouses) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, warehouses.size()).forEach(index -> order.put(warehouses.get(index).getId(), index));
        List<Warehouse> result = entityManager
            .createQuery(
                "select warehouse from Warehouse warehouse left join fetch warehouse.products where warehouse in :warehouses",
                Warehouse.class
            )
            .setParameter(WAREHOUSES_PARAMETER, warehouses)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
