package com.rimba.inventory;

import com.rimba.inventory.enums.RoleEnum;
import com.rimba.inventory.models.Role;
import com.rimba.inventory.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RimbaInvetoryRailwayApplication implements CommandLineRunner {

	@Autowired
	RoleRepository roleRepository;

	public static void main(String[] args) {

		SpringApplication.run(RimbaInvetoryRailwayApplication.class, args);

	}

	@Override
	public void run(String... args) {
		Role role1 = new Role(RoleEnum.ROLE_ADMIN);
		roleRepository.save(role1);

		Role role2 = new Role(RoleEnum.ROLE_USER);
		roleRepository.save(role2);
	}
}
