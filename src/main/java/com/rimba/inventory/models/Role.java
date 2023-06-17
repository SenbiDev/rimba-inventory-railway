package com.rimba.inventory.models;

import com.rimba.inventory.enums.RoleEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@Data
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  public Role (RoleEnum name) {
    this.name = name;
  }

  @Enumerated(EnumType.STRING)
  @Column(length = 20)
  private RoleEnum name;
}