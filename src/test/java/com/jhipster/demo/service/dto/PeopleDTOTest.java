package com.jhipster.demo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.jhipster.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PeopleDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PeopleDTO.class);
        PeopleDTO peopleDTO1 = new PeopleDTO();
        peopleDTO1.setId(1L);
        PeopleDTO peopleDTO2 = new PeopleDTO();
        assertThat(peopleDTO1).isNotEqualTo(peopleDTO2);
        peopleDTO2.setId(peopleDTO1.getId());
        assertThat(peopleDTO1).isEqualTo(peopleDTO2);
        peopleDTO2.setId(2L);
        assertThat(peopleDTO1).isNotEqualTo(peopleDTO2);
        peopleDTO1.setId(null);
        assertThat(peopleDTO1).isNotEqualTo(peopleDTO2);
    }
}
