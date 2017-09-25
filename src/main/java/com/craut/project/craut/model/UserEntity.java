package com.craut.project.craut.model;



import lombok.Getter;
import lombok.Setter;
import org.jboss.logging.annotations.Field;

import javax.persistence.*;
@Setter
@Getter
@Entity
@Table(name = "user")
public class UserEntity{
    @Id
    @Column(name = "iduser")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long iduser;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "email")
    private String email;


    @Column(name = "userName")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "blocked")
    private int blocked;

    @Column(name = "image")
    private String image;
    public UserEntity() {
    }

    public UserEntity(String firstName, String lastName, String email, String userName, String password, int blocked,String image) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.blocked = blocked;
        this.image = image;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "iduser=" + iduser +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", nickName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", blocked=" + blocked +
                '}';
    }
}
