package com.jhipster.demo.domain;

import static com.jhipster.demo.domain.ProductTestSamples.*;
import static com.jhipster.demo.domain.WarehouseTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.jhipster.demo.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Product.class);
        Product product1 = getProductSample1();
        Product product2 = new Product();
        assertThat(product1).isNotEqualTo(product2);

        product2.setId(product1.getId());
        assertThat(product1).isEqualTo(product2);

        product2 = getProductSample2();
        assertThat(product1).isNotEqualTo(product2);
    }

    @Test
    void warehousesTest() {
        Product product = getProductRandomSampleGenerator();
        Warehouse warehouseBack = getWarehouseRandomSampleGenerator();

        product.addWarehouses(warehouseBack);
        assertThat(product.getWarehouses()).containsOnly(warehouseBack);
        assertThat(warehouseBack.getProducts()).containsOnly(product);

        product.removeWarehouses(warehouseBack);
        assertThat(product.getWarehouses()).doesNotContain(warehouseBack);
        assertThat(warehouseBack.getProducts()).doesNotContain(product);

        product.warehouses(new HashSet<>(Set.of(warehouseBack)));
        assertThat(product.getWarehouses()).containsOnly(warehouseBack);
        assertThat(warehouseBack.getProducts()).containsOnly(product);

        product.setWarehouses(new HashSet<>());
        assertThat(product.getWarehouses()).doesNotContain(warehouseBack);
        assertThat(warehouseBack.getProducts()).doesNotContain(product);
    }
}
