package com.craut.project.craut.model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class RolesEntity {

    @Id
    @Column(name = "idroles")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idroles;

    @Column(name = "roleStatus")
    private String roleStatus;

}
