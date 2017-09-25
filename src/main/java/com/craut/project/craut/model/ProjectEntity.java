package com.craut.project.craut.model;
import javax.persistence.*;

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

//    @ManyToOne(cascade = CascadeType.ALL)
    @ManyToOne
    @JoinColumn(name = "iduser")
    private UserEntity user;

    @Column(name = "rating")
    private int rating;

//    @ManyToOne(cascade = CascadeType.ALL)
    @ManyToOne
    @JoinColumn(name = "statusid")
    private StatusEntity statusEntity;

    @Column(name = "content")
    private String content;

    public ProjectEntity() {
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

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

//    public UserEntity getIduser() {
//        return iduser;
//    }
//
//    public void setIduser(UserEntity iduser) {
//        this.iduser = iduser;
//    }

    public Long getIdproject() {
        return idproject;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setIdproject(Long idproject) {
        this.idproject = idproject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDwy() {
        return dwy;
    }

    public void setDwy(String dwy) {
        this.dwy = dwy;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public StatusEntity getStatusEntity() {
        return statusEntity;
    }

    public void setStatusEntity(StatusEntity statusEntity) {
        this.statusEntity = statusEntity;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
