package com.craut.project.craut.model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "project")
public class ProjectEntity {

    @Id
    @Column(name = "idproject")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idproject;

    @Column(name = "name")
    private String name;

    @Column(name = "dwy")
    private String dwy;

    @Column(name = "image")
    private String image;

    @Column(name = "purpose")
    private String purpose;

    @Column(name = "money")
    private int money;

    @Column(name = "cash")
    private int cash;

    @ManyToOne
    @JoinColumn(name = "iduser")
    private UserEntity user;

    @Column(name = "rating")
    private int rating;

    @ManyToOne
    @JoinColumn(name = "statusid")
    private StatusEntity statusEntity;

    @Column(name = "content")
    private String content;

    public ProjectEntity(String name, String dwy, String image, String purpose, int money, int rating,
                         StatusEntity statusEntity, String content,int cash, UserEntity user) {
        this.name = name;
        this.dwy = dwy;
        this.image = image;
        this.purpose = purpose;
        this.money = money;
        this.rating = rating;
        this.statusEntity = statusEntity;
        this.content = content;
        this.cash = cash;
        this.user = user;
    }
}
