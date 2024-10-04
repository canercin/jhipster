package com.jhipster.demo.service.mapper;

import com.jhipster.demo.domain.People;
import com.jhipster.demo.service.dto.PeopleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link People} and its DTO {@link PeopleDTO}.
 */
@Mapper(componentModel = "spring")
public interface PeopleMapper extends EntityMapper<PeopleDTO, People> {}
