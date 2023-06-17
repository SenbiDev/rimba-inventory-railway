package com.rimba.inventory.controller;

import com.rimba.inventory.exception.ResourceNotFoundException;
import com.rimba.inventory.enums.RoleEnum;
import com.rimba.inventory.models.Role;
import com.rimba.inventory.models.User;
import com.rimba.inventory.payload.request.LoginRequest;
import com.rimba.inventory.payload.request.SignupRequest;
import com.rimba.inventory.payload.response.JwtData;
import com.rimba.inventory.payload.response.AuthResponse;
import com.rimba.inventory.payload.response.JwtResponse;
import com.rimba.inventory.repository.RoleRepository;
import com.rimba.inventory.repository.UserRepository;
import com.rimba.inventory.security.jwt.JwtUtils;
import com.rimba.inventory.security.services.UserDetailsImpl;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @PostMapping("/login")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);
    
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();    
    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                    new JwtResponse(
                            HttpStatus.OK.value(),
                            "success",
                            new JwtData(
                                    jwt,
                                    userDetails.getId(),
                                    userDetails.getUsername(),
                                    userDetails.getEmail(),
                                    roles
                            )
                    )
            );
  }

  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity
          .status(HttpStatus.CONFLICT)
          .body(
                  new AuthResponse(
                    HttpStatus.CONFLICT.value(),
                    "Conflict",
                    "Username is created!"
                  )
          );
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
              .status(HttpStatus.CONFLICT)
              .body(
                      new AuthResponse(
                              HttpStatus.CONFLICT.value(),
                              "Conflict",
                              "Email is created!"
                      )
              );
    }

    User user = new User(
            signUpRequest.getUsername(),
            signUpRequest.getEmail(),
            encoder.encode(signUpRequest.getPassword())
    );

    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
          .orElseThrow(() -> new ResourceNotFoundException(String.format("Role %s is not found", null)));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
        case "admin":
          Role adminRole = roleRepository.findByName(RoleEnum.ROLE_ADMIN)
              .orElseThrow(() -> new ResourceNotFoundException(String.format("Role %s is not found", strRoles)));
          roles.add(adminRole);

          break;

        default:
          Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
              .orElseThrow(() -> new ResourceNotFoundException(String.format("Role %s is not found", strRoles)));
          roles.add(userRole);
        }
      });
    }

    user.setRoles(roles);
    userRepository.save(user);

    return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                    new AuthResponse(
                            HttpStatus.OK.value(),
                            null,
                            "new user saved!"
                    )
            );
  }
}
