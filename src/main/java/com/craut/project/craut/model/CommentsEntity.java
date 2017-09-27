package com.craut.project.craut.model;
import javax.persistence.*;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class CommentsEntity {

    @Id
    @Column(name = "idcomments")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idcomments;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "projectid")
    private ProjectEntity projectEntity;

    @ManyToOne
    @JoinColumn(name = "userid")
    private UserEntity userEntity;

    public CommentsEntity(String comment, ProjectEntity projectEntity, UserEntity userEntity) {
        this.comment = comment;
        this.projectEntity = projectEntity;
        this.userEntity = userEntity;
    }
}
