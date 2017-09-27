package com.craut.project.craut.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "status")
public class StatusEntity {

    @Id
    @Column(name = "idstatus")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idstatus;

    @Column(name = "status")
    private String status;

    public StatusEntity(String status) {
        this.status = status;
    }
}
