package com.craut.project.craut.model;
import javax.persistence.*;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
@Entity
@Table(name = "comments")
public class CommentsEntity {

    @Id
    @Column(name = "idcomments")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idcomments;

    @Column(name = "comment")
    private String comment;

//    @ManyToOne(cascade = CascadeType.ALL)
    @ManyToOne
    @JoinColumn(name = "projectid")
    private ProjectEntity projectEntity;

//    @ManyToOne(cascade = CascadeType.ALL)
    @ManyToOne
    @JoinColumn(name = "userid")
    private UserEntity userEntity;

    public CommentsEntity(){}

    public CommentsEntity(String comment, ProjectEntity projectEntity, UserEntity userEntity) {
        this.comment = comment;
        this.projectEntity = projectEntity;
        this.userEntity = userEntity;
    }
}
