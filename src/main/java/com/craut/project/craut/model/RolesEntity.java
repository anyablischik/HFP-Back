package com.craut.project.craut.model;
import javax.persistence.*;

@Entity
@Table(name = "roles")
public class RolesEntity {

    @Id
    @Column(name = "idroles")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idroles;

    @Column(name = "roleStatus")
    private String roleStatus;

    public RolesEntity(String roleStatus) {
        this.roleStatus = roleStatus;
    }

    public RolesEntity() {
    }

    public Long getIdroles() {
        return idroles;
    }

    public void setIdroles(Long idroles) {
        this.idroles = idroles;
    }

    public String getRoleStatus() {
        return roleStatus;
    }

    public void setRoleStatus(String firstName) {
        this.roleStatus = firstName;
    }

    @Override
    public String toString() {
        return "RolesEntity{" +
                "idroles=" + idroles +
                ", firstName='" + roleStatus + '\'' +
                '}';
    }
}
