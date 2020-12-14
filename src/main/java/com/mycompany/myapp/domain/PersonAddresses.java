package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.mycompany.myapp.domain.enumeration.TestType;

/**
 * A PersonAddresses.
 */
@Entity
@Table(name = "person_addresses")
public class PersonAddresses implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "zipcode")
    private Integer zipcode;

    @Enumerated(EnumType.STRING)
    @Column(name = "test")
    private TestType test;

    @ManyToOne
    @JsonIgnoreProperties(value = "addresses", allowSetters = true)
    private Person person;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public PersonAddresses address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getZipcode() {
        return zipcode;
    }

    public PersonAddresses zipcode(Integer zipcode) {
        this.zipcode = zipcode;
        return this;
    }

    public void setZipcode(Integer zipcode) {
        this.zipcode = zipcode;
    }

    public TestType getTest() {
        return test;
    }

    public PersonAddresses test(TestType test) {
        this.test = test;
        return this;
    }

    public void setTest(TestType test) {
        this.test = test;
    }

    public Person getPerson() {
        return person;
    }

    public PersonAddresses person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonAddresses)) {
            return false;
        }
        return id != null && id.equals(((PersonAddresses) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonAddresses{" +
            "id=" + getId() +
            ", address='" + getAddress() + "'" +
            ", zipcode=" + getZipcode() +
            ", test='" + getTest() + "'" +
            "}";
    }
}
