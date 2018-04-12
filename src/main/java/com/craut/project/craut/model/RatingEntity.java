package com.craut.project.craut.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "rating")
@Getter
@Setter
@NoArgsConstructor
public class RatingEntity {

    @Id
    @Column(name = "idrating")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idrating;

    @Column(name="fifthStarCnt")
    private Integer fifthStarCnt;

    @Column(name="fourthStarCnt")
    private Integer fourthStarCnt;

    @Column(name="thirdStarCnt")
    private Integer thirdStarCnt;

    @Column(name="secondStarCnt")
    private Integer secondStarCnt;

    @Column(name="firstStarCnt")
    private Integer firstStarCnt;

    @Column(name="value")
    private Double value;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idInstruction")
    private InstructionEntity instructionEntity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "iduser")
    private UserEntity userEntite;

    public RatingEntity(InstructionEntity instructionEntity, UserEntity userEntite, Double value,
                        Integer firstStarCnt, Integer secondStarCnt, Integer thirdStarCnt,
                        Integer fourthStarCnt, Integer fifthStarCnt) {
        this.instructionEntity = instructionEntity;
        this.userEntite = userEntite;
        this.value = value;
        this.firstStarCnt = firstStarCnt;
        this.secondStarCnt = secondStarCnt;
        this.thirdStarCnt = thirdStarCnt;
        this.fourthStarCnt = fourthStarCnt;
        this.fifthStarCnt = fifthStarCnt;
    }
}