package com.jhipster.demo.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.jhipster.demo.domain.People} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PeopleDTO implements Serializable {

    private Long id;

    private String firstname;

    private String lastname;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PeopleDTO)) {
            return false;
        }

        PeopleDTO peopleDTO = (PeopleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, peopleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PeopleDTO{" +
            "id=" + getId() +
            ", firstname='" + getFirstname() + "'" +
            ", lastname='" + getLastname() + "'" +
            "}";
    }
}
