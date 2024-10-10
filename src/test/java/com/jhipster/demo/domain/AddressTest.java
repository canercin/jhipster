package com.jhipster.demo.domain;

import static com.jhipster.demo.domain.AddressTestSamples.*;
import static com.jhipster.demo.domain.PeopleTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.jhipster.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AddressTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Address.class);
        Address address1 = getAddressSample1();
        Address address2 = new Address();
        assertThat(address1).isNotEqualTo(address2);

        address2.setId(address1.getId());
        assertThat(address1).isEqualTo(address2);

        address2 = getAddressSample2();
        assertThat(address1).isNotEqualTo(address2);
    }

    @Test
    void peopleTest() {
        Address address = getAddressRandomSampleGenerator();
        People peopleBack = getPeopleRandomSampleGenerator();

        address.setPeople(peopleBack);
        assertThat(address.getPeople()).isEqualTo(peopleBack);

        address.people(null);
        assertThat(address.getPeople()).isNull();
    }
}
