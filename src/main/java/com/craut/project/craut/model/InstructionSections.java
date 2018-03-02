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
    private Long id;

    @Column(name = "nameSection")
    private String title;


    public InstructionSections(String nameSection) {
        this.title = nameSection;
    }
}