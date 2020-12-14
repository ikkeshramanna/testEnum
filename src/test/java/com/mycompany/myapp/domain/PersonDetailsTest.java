package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class PersonDetailsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonDetails.class);
        PersonDetails personDetails1 = new PersonDetails();
        personDetails1.setId(1L);
        PersonDetails personDetails2 = new PersonDetails();
        personDetails2.setId(personDetails1.getId());
        assertThat(personDetails1).isEqualTo(personDetails2);
        personDetails2.setId(2L);
        assertThat(personDetails1).isNotEqualTo(personDetails2);
        personDetails1.setId(null);
        assertThat(personDetails1).isNotEqualTo(personDetails2);
    }
}
