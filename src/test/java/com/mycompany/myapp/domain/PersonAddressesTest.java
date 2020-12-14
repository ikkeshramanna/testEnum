package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class PersonAddressesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonAddresses.class);
        PersonAddresses personAddresses1 = new PersonAddresses();
        personAddresses1.setId(1L);
        PersonAddresses personAddresses2 = new PersonAddresses();
        personAddresses2.setId(personAddresses1.getId());
        assertThat(personAddresses1).isEqualTo(personAddresses2);
        personAddresses2.setId(2L);
        assertThat(personAddresses1).isNotEqualTo(personAddresses2);
        personAddresses1.setId(null);
        assertThat(personAddresses1).isNotEqualTo(personAddresses2);
    }
}
