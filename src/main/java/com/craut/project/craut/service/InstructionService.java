package com.craut.project.craut.service;

import com.craut.project.craut.model.*;
import com.craut.project.craut.repository.GenericDaoImpl;
import com.craut.project.craut.service.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class InstructionService {
    @Autowired
    GenericDaoImpl genericDaoImpl;
    public String save(InstructionRequestDto instructionRequestDto, List<Object> tags)
    {
        InstructionEntity instructionEntity = new InstructionEntity(instructionRequestDto.getName(), instructionRequestDto.getTheme(), instructionRequestDto.getRating(),
                (StepEntity) genericDaoImpl.findById(new StepEntity(), Long.parseLong(instructionRequestDto.getStep().toString())),
                (UserEntity) genericDaoImpl.findById(new UserEntity(),Long.parseLong(instructionRequestDto.getUser().toString())),
                        (InstructionSections) genericDaoImpl.findById(new InstructionSections(), Long.parseLong(instructionRequestDto.getSections().toString())));
        genericDaoImpl.save(instructionEntity);
        for(Object tag:tags){
            tag = tag.toString().split("value=")[1].split("}")[0];
            TagsEntity tagsEntity = new TagsEntity(tag.toString(), instructionEntity);
            genericDaoImpl.save(tagsEntity);
        }
        return "success";
    }

    public ProjectAndTagsRequestDto getProject(Object idproject){
        InstructionEntity instructionEntity = (InstructionEntity) genericDaoImpl.findById(new InstructionEntity(),
                Long.parseLong(idproject.toString()));
        InstructionRequestDto instructionRequestDto = new InstructionRequestDto(instructionEntity.getRating(), instructionEntity.getNameInstruction(),
                instructionEntity.getTheme(),"" + instructionEntity.getStep().getIdStep(), ""+ instructionEntity.getUser().getIdUser(),
                "" + instructionEntity.getSections().getIdSection());
        ProjectAndTagsRequestDto projectAndTagsRequestDto = new ProjectAndTagsRequestDto(instructionRequestDto,
                genericDaoImpl.findTagByProject(instructionEntity,"TagsEntity","instructionEntity"),
                genericDaoImpl.findCommentByProject(instructionEntity,"CommentsEntity","instructionEntity").getComment(),
                genericDaoImpl.findCommentByProject(instructionEntity,"CommentsEntity","instructionEntity").getUser());
        return projectAndTagsRequestDto;
    }

    public void AddComment(CommentRequestDto commentRequestDto){
        System.out.println(commentRequestDto.getIdproject() + " "+ commentRequestDto.getIduser());
        InstructionEntity instructionEntity = (InstructionEntity)genericDaoImpl.findById(new InstructionEntity(),commentRequestDto.getIdproject());
        UserEntity userEntity = (UserEntity)genericDaoImpl.findById(new UserEntity(),commentRequestDto.getIduser());

        CommentsEntity commentsEntity = new CommentsEntity(commentRequestDto.getComment(), instructionEntity,userEntity);
        genericDaoImpl.save(commentsEntity);
    }

    public void addTags(InstructionEntity instructionEntity, List<Object> tags){
        for(Object tag:tags){
            TagsEntity tagsEntity = new TagsEntity(tag.toString(), instructionEntity);
            genericDaoImpl.save(tagsEntity);
        }

    }

    public List<InstructionRequestDto> getUserProjects(Object data){
        return genericDaoImpl.findListByParametr((UserEntity)genericDaoImpl.findById(new UserEntity(),
                Long.parseLong(data.toString())),"InstructionEntity","user");
    }
    public List<InstructionRequestDto> getProjects(Long data){
        if(data == 0) {
            return genericDaoImpl.list("InstructionEntity");
        }
        else if(data == 1){
            return genericDaoImpl.sortByAsc("InstructionEntity","rating");
        }
        else {
            if(data == 2){
                return genericDaoImpl.sortByAsc("InstructionEntity","dwy");
            }else
            return genericDaoImpl.list("InstructionEntity");
        }
    }

    public String setRating(RatingRequestDto rating){
        InstructionEntity currentProject = (InstructionEntity)genericDaoImpl.findById
                (new InstructionEntity(),Long.parseLong(rating.getIdproject().toString()));
        UserEntity userEntity = (UserEntity) genericDaoImpl.findById
                (new UserEntity(),Long.parseLong(rating.getIduser().toString()));
        RatingEntity ratingEntity = (RatingEntity)genericDaoImpl.findByTwoParametr(currentProject,
                "RatingEntity","instructionEntity","userEntite", userEntity);
        if(ratingEntity==null) {
            if (currentProject.getRating() == 0) {
                genericDaoImpl.update("InstructionEntity", "idproject", currentProject.getIdInstruction(),
                        "rating", rating.getRating());
            } else {
                genericDaoImpl.update("InstructionEntity", "idproject", currentProject.getIdInstruction(),
                        "rating",
                        (currentProject.getRating() + rating.getRating()) / 2);
            }
            genericDaoImpl.save(new RatingEntity(currentProject,userEntity));
            return "succes";
        }
        return "You alreade give rating";
    }

    public void checkProject()
    {
        List<InstructionEntity> instructionEntityList = genericDaoImpl.list("InstructionEntity");
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd");
        StatusEntity active = (StatusEntity)genericDaoImpl.findById(new StatusEntity(),1l);
    }

    public List<InstructionEntity> searcheByTag(Object tag){
        return genericDaoImpl.findProjectByTag(tag.toString(),"TagsEntity","name");
    }
}
