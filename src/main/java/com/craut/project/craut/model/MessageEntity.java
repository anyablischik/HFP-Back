package com.craut.project.craut.model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "message")
public class MessageEntity {

    @Id
    @Column(name = "idmassage")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idmessage;

    @Column(name = "text")
    private String text;

    @Column(name = "theme")
    private String theme;

    @Column(name = "image")
    private String image;

    @ManyToOne
    @JoinColumn(name = "user")
    UserEntity user;

    public MessageEntity(String text, String theme, String image, UserEntity user) {
        this.text = text;
        this.theme = theme;
        this.image = image;
        this.user = user;
    }
}


