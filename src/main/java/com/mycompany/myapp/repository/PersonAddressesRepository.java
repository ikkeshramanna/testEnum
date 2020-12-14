package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PersonAddresses;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PersonAddresses entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonAddressesRepository extends JpaRepository<PersonAddresses, Long> {
}
