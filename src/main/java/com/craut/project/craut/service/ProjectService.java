package com.craut.project.craut.service;

import com.craut.project.craut.model.*;
import com.craut.project.craut.repository.GenericDaoImpl;
import com.craut.project.craut.service.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectService {
    @Autowired
    GenericDaoImpl genericDaoImpl;
    public String save(ProjectRequestDto projectRequestDto, List<Object> tags)
    {
        ProjectEntity projectEntity = new ProjectEntity(projectRequestDto.getName(),projectRequestDto.getDwy(),
                projectRequestDto.getImage(),projectRequestDto.getPurpose(),projectRequestDto.getMoney(),0,
                (StatusEntity) genericDaoImpl.findById(new StatusEntity(),1L),
                projectRequestDto.getContent(),0,
                (UserEntity) genericDaoImpl.findById(new UserEntity(),Long.parseLong(projectRequestDto.getUser().toString()))
        );
        genericDaoImpl.save(projectEntity);
        for(Object tag:tags){
            tag = tag.toString().split("value=")[1].split("}")[0];
            TagsEntity tagsEntity = new TagsEntity(tag.toString(),projectEntity);
            genericDaoImpl.save(tagsEntity);
        }
        return "success";
    }
    public ProjectAndTagsRequestDto getProject(Object idproject){
        ProjectEntity projectEntity = (ProjectEntity) genericDaoImpl.findById(new ProjectEntity(),
                Long.parseLong(idproject.toString()));
        ProjectRequestDto projectRequestDto = new ProjectRequestDto(projectEntity.getContent(),projectEntity.getDwy(),
                projectEntity.getImage(),
                projectEntity.getMoney(),projectEntity.getRating(),projectEntity.getName(),projectEntity.getPurpose(),
                ""+projectEntity.getUser().getIduser(),
                projectEntity.getStatusEntity().getStatus(),projectEntity.getCash());
        ProjectAndTagsRequestDto projectAndTagsRequestDto = new ProjectAndTagsRequestDto(projectRequestDto,
                genericDaoImpl.findTagByProject(projectEntity,"TagsEntity","projectEntity"),
                genericDaoImpl.findCommentByProject(projectEntity,"CommentsEntity","projectEntity").getComment(),
                genericDaoImpl.findCommentByProject(projectEntity,"CommentsEntity","projectEntity").getUser());
        return projectAndTagsRequestDto;
    }

    public void addMoney(ProjectMoney projectMoney){
        if(projectMoney.getMoney() > 0) {
            ProjectEntity projectEntity = (ProjectEntity) genericDaoImpl.findById(new ProjectEntity(), projectMoney.getIdProject());
            projectEntity.setCash(projectEntity.getCash() + projectMoney.getMoney());
            genericDaoImpl.save(projectEntity);
        }
    }
    public void AddComment(CommentRequestDto commentRequestDto){
        System.out.println(commentRequestDto.getIdproject() + " "+ commentRequestDto.getIduser());
        ProjectEntity projectEntity = (ProjectEntity)genericDaoImpl.findById(new ProjectEntity(),commentRequestDto.getIdproject());
        UserEntity userEntity = (UserEntity)genericDaoImpl.findById(new UserEntity(),commentRequestDto.getIduser());

        CommentsEntity commentsEntity = new CommentsEntity(commentRequestDto.getComment(),projectEntity,userEntity);
        genericDaoImpl.save(commentsEntity);
    }

    public void addTags(ProjectEntity projectEntity,List<Object> tags){
        for(Object tag:tags){
            TagsEntity tagsEntity = new TagsEntity(tag.toString(),projectEntity);
            genericDaoImpl.save(tagsEntity);
        }

    }

    public List<ProjectRequestDto> getUserProjects(Object data){
        return genericDaoImpl.findListByParametr((UserEntity)genericDaoImpl.findById(new UserEntity(),
                Long.parseLong(data.toString())),"ProjectEntity","user");
    }
    public List<ProjectRequestDto> getProjects(Long data){
        if(data == 0) {
            return genericDaoImpl.list("ProjectEntity");
        }
        else if(data == 1){
            return genericDaoImpl.sortByAsc("ProjectEntity","rating");
        }
        else {
            if(data == 2){
                return genericDaoImpl.sortByAsc("ProjectEntity","dwy");
            }else
            return genericDaoImpl.list("ProjectEntity");
        }
    }

    public String setRating(RatingRequestDto rating){
        ProjectEntity currentProject = (ProjectEntity)genericDaoImpl.findById
                (new ProjectEntity(),Long.parseLong(rating.getIdproject().toString()));
        UserEntity userEntity = (UserEntity) genericDaoImpl.findById
                (new UserEntity(),Long.parseLong(rating.getIduser().toString()));
        RatingEntity ratingEntity = (RatingEntity)genericDaoImpl.findByTwoParametr(currentProject,
                "RatingEntity","projectEntity","userEntite", userEntity);
        if(ratingEntity==null) {
            if (currentProject.getRating() == 0) {
                genericDaoImpl.update("ProjectEntity", "idproject", currentProject.getIdproject(),
                        "rating", rating.getRating());
            } else {
                genericDaoImpl.update("ProjectEntity", "idproject", currentProject.getIdproject(),
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
        List<ProjectEntity> projectEntityList = genericDaoImpl.list("ProjectEntity");
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd");
        StatusEntity active = (StatusEntity)genericDaoImpl.findById(new StatusEntity(),1l);
        for(ProjectEntity projectEntity:projectEntityList)
        {
            if(projectEntity.getCash()>=projectEntity.getMoney())
            {
                genericDaoImpl.update("ProjectEntity","idproject",projectEntity.getIdproject(),
                        "statusEntity",3L);
            }
            if (formatForDateNow.format(dateNow).compareTo(projectEntity.getDwy()) > 0 ) {
                genericDaoImpl.update("ProjectEntity", "idproject", projectEntity.getIdproject(),
                        "statusEntity", 2L);
            }
        }
    }

    public List<ProjectEntity> searcheByTag(Object tag){
        return genericDaoImpl.findProjectByTag(tag.toString(),"TagsEntity","name");
    }
}
