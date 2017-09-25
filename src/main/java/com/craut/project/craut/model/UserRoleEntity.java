package com.craut.project.craut.model;

import javax.persistence.*;

@Entity
@Table(name = "users_roles")
public class UserRoleEntity {
    @Id
    @Column(name = "idusers_roles")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idusers_roles;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idUser")
    private UserEntity user;

//    @ManyToOne(cascade = CascadeType.ALL)
    @ManyToOne
    @JoinColumn(name = "idRole")
    private RolesEntity role;

    public UserRoleEntity(UserEntity user, RolesEntity role) {
        this.user = user;
        this.role = role;
    }

    public UserRoleEntity() {
    }

    public Long getIdusers_roles() {
        return idusers_roles;
    }

    public void setIdusers_roles(Long idusers_roles) {
        this.idusers_roles = idusers_roles;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public RolesEntity getRole() {
        return role;
    }

    public void setRole(RolesEntity role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserRoleEntity{" +
                "idusers_roles=" + idusers_roles +
                ", user=" + user +
                ", role=" + role +
                '}';
    }
}
