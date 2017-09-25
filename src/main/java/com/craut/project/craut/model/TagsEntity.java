package com.craut.project.craut.model;
import javax.persistence.*;

@Entity
@Table(name = "tags")
public class TagsEntity {
    @Id
    @Column(name = "idtags")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idtags;

    @Column(name = "name")
    private String name;

//    @ManyToOne(cascade = CascadeType.ALL)
    @ManyToOne
    @JoinColumn(name = "idproject")
    private ProjectEntity projectEntity;

    public Integer getIdtags() {
        return idtags;
    }

    public void setIdtags(Integer idtags) {
        this.idtags = idtags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProjectEntity getProjectEntity() {
        return projectEntity;
    }

    public void setProjectEntity(ProjectEntity projectEntity) {
        this.projectEntity = projectEntity;
    }

    public TagsEntity() {

    }

    public TagsEntity(String tagsName,ProjectEntity projectEntity) {
        this.projectEntity = projectEntity;
        this.name = tagsName;
    }
}
