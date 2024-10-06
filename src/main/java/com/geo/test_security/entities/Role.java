package com.geo.test_security.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name="tb_roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Enumerated(value = EnumType.STRING)
    private Values name;

    @ManyToMany(mappedBy = "role")
    private Set<User> user;

    public enum Values {
        BASIC,
        ADMIN;
    }
}
