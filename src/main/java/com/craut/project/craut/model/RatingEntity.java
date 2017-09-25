package com.craut.project.craut.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "rating")
@Getter
@Setter
@NoArgsConstructor
public class RatingEntity {

    @Id
    @Column(name = "idrating")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idrating;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idproject")
    private ProjectEntity projectEntity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "iduser")
    private UserEntity userEntite;

    public RatingEntity(ProjectEntity projectEntity,UserEntity userEntite) {
        this.projectEntity = projectEntity;
        this.userEntite = userEntite;
    }
}