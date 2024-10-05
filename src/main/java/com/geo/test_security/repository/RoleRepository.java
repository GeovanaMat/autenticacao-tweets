package com.geo.test_security.repository;

import com.geo.test_security.entities.Role;
import com.geo.test_security.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(Role.Values name);
}
