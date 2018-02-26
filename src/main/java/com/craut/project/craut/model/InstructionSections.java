package com.craut.project.craut.model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "instructionSections")
public class InstructionSections {

    @Id
    @Column(name = "idSection")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idSection;

    @Column(name = "nameSection")
    private String nameSection;


    public InstructionSections(Long idSection, String nameSection) {
        this.nameSection = nameSection;
        this.idSection = idSection;
    }
}