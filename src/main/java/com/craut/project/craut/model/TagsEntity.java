package com.craut.project.craut.model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tags")
public class TagsEntity {
    @Id
    @Column(name = "idtags")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idtags;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "idproject")
    private ProjectEntity projectEntity;

    public TagsEntity(String tagsName,ProjectEntity projectEntity) {
        this.projectEntity = projectEntity;
        this.name = tagsName;
    }
}
