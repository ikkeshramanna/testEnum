package com.mycompany.myapp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Person.
 */
@Entity
@Table(name = "person")
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "age")
    private Integer age;

    @OneToMany(mappedBy = "person")
    private Set<PersonAddresses> addresses = new HashSet<>();

    @OneToMany(mappedBy = "person")
    private Set<PersonDetails> details = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Person name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public Person age(Integer age) {
        this.age = age;
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Set<PersonAddresses> getAddresses() {
        return addresses;
    }

    public Person addresses(Set<PersonAddresses> personAddresses) {
        this.addresses = personAddresses;
        return this;
    }

    public Person addAddresses(PersonAddresses personAddresses) {
        this.addresses.add(personAddresses);
        personAddresses.setPerson(this);
        return this;
    }

    public Person removeAddresses(PersonAddresses personAddresses) {
        this.addresses.remove(personAddresses);
        personAddresses.setPerson(null);
        return this;
    }

    public void setAddresses(Set<PersonAddresses> personAddresses) {
        this.addresses = personAddresses;
    }

    public Set<PersonDetails> getDetails() {
        return details;
    }

    public Person details(Set<PersonDetails> personDetails) {
        this.details = personDetails;
        return this;
    }

    public Person addDetails(PersonDetails personDetails) {
        this.details.add(personDetails);
        personDetails.setPerson(this);
        return this;
    }

    public Person removeDetails(PersonDetails personDetails) {
        this.details.remove(personDetails);
        personDetails.setPerson(null);
        return this;
    }

    public void setDetails(Set<PersonDetails> personDetails) {
        this.details = personDetails;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Person)) {
            return false;
        }
        return id != null && id.equals(((Person) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Person{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", age=" + getAge() +
            "}";
    }
}
