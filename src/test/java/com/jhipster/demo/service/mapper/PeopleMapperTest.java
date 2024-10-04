package com.jhipster.demo.service.mapper;

import static com.jhipster.demo.domain.PeopleAsserts.*;
import static com.jhipster.demo.domain.PeopleTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PeopleMapperTest {

    private PeopleMapper peopleMapper;

    @BeforeEach
    void setUp() {
        peopleMapper = new PeopleMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPeopleSample1();
        var actual = peopleMapper.toEntity(peopleMapper.toDto(expected));
        assertPeopleAllPropertiesEquals(expected, actual);
    }
}
