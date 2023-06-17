package com.rimba.inventory.repository;

import com.rimba.inventory.enums.RoleEnum;
import com.rimba.inventory.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(RoleEnum name);
}
