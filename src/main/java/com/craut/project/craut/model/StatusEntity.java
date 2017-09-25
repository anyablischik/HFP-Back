package com.craut.project.craut.model;

import javax.persistence.*;

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

    public StatusEntity() {
    }

    public Long getIdstatus() {
        return idstatus;
    }

    public void setIdstatus(Long idstatus) {
        this.idstatus = idstatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
