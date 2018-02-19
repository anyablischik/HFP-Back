package com.craut.project.craut.model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "instructions")
public class InstructionEntity {

    @Id
    @Column(name = "idInstruction")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idInstruction;

    @Column(name = "nameInstruction")
    private String nameInstruction;

    @Column(name = "theme")
    private String theme;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private UserEntity user;

    @OneToOne
    @JoinColumn(name = "idSection")
    private InstructionSections sections;

    @Column(name = "rating")
    private int rating;


    public InstructionEntity(String nameInstruction, String theme, int rating, UserEntity user, InstructionSections sections) {
        this.nameInstruction = nameInstruction;
        this.theme = theme;
        this.rating = rating;
        this.user = user;
        this.sections = sections;
    }
}
