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
    @JoinColumn(name = "instructionid")
    private InstructionEntity instructionEntity;

    @ManyToOne
    @JoinColumn(name = "userid")
    private UserEntity userEntity;

    public CommentsEntity(String comment, InstructionEntity instructionEntity, UserEntity userEntity) {
        this.comment = comment;
        this.instructionEntity = instructionEntity;
        this.userEntity = userEntity;
    }
}
