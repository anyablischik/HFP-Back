package com.craut.project.craut.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users_roles")
public class UserRoleEntity {
    @Id
    @Column(name = "idusers_roles")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idusers_roles;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idUser")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "idRole")
    private RolesEntity role;

    public UserRoleEntity(UserEntity user, RolesEntity role) {
        this.user = user;
        this.role = role;
    }

}
