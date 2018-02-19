package com.craut.project.craut.model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.xml.soap.Text;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "steps")
public class StepEntity {

    @Id
    @Column(name = "idStep")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idStep;

    @Column(name = "nameStep")
    private String nameStep;

    @Column(name = "image")
    private String image;

    @Column(name = "text")
    private String text;


    public StepEntity(String nameStep, String image, String text) {
        this.nameStep = nameStep;
        this.image = image;
        this.text = text;
    }
}
