package com.craut.project.craut.service;

import com.craut.project.craut.model.*;
import com.craut.project.craut.repository.GenericDaoImpl;
import com.craut.project.craut.service.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class InstructionService {
    @Autowired
    GenericDaoImpl genericDaoImpl;
    public InstructionAndTagsRequestDto save(InstructionAndTagsRequestDto instructionAndTagsRequestDto, List<Object> tags, List<StepDto> steps, SectionDto section, Long userId, Integer rating, String title)
    {
        if(rating == null) {
            rating = 0;
        }
            InstructionEntity instructionEntity = new InstructionEntity(title, rating,
                    (UserEntity) genericDaoImpl.findById(new UserEntity(), userId),
                    (InstructionSections) genericDaoImpl.findById(new InstructionSections(), section.getId()));
            genericDaoImpl.save(instructionEntity);
        if(tags != null) {
            for (Object tag : tags) {
                TagsEntity tagsEntity = new TagsEntity(tag.toString(), instructionEntity);
                genericDaoImpl.save(tagsEntity);
            }
        }

        for(StepDto step: steps){
            if(step.getImage() == null){
                step.setImage("");
            }
            StepEntity stepEntity = new StepEntity(step.getName(), step.getImage().toString(), step.getText(), (InstructionEntity) genericDaoImpl.findById(new InstructionEntity(), instructionEntity.getIdInstruction()),step.getPosition());
            genericDaoImpl.save(stepEntity);
        }
        instructionAndTagsRequestDto.setId(instructionEntity.getIdInstruction());
        return instructionAndTagsRequestDto;
    }

    public StepDto save(StepDto step)
    {
        String linkImage = step.getImage() != null ? step.getImage().toString() : "";
        StepEntity stepEntity = new StepEntity(step.getName(),linkImage , step.getText(), (InstructionEntity) genericDaoImpl.findById(new InstructionEntity(), step.getInstructionId()),step.getPosition());
        genericDaoImpl.save(stepEntity);
        step.setId(stepEntity.getIdStep());
        return step;
    }

    public StepDto updateStep(Long id, StepDto step){
        StepEntity stepEntity = (StepEntity) genericDaoImpl.findById(new StepEntity(), id);
        stepEntity.setNameStep(step.getName());
        if(step.getImage() == null){
            step.setImage("");
        }
        stepEntity.setImage(step.getImage().toString());
        stepEntity.setText(step.getText());
        stepEntity.setPosition(step.getPosition());
        stepEntity.setInstruction((InstructionEntity) genericDaoImpl.findById(new InstructionEntity(),step.getInstructionId()));
        genericDaoImpl.save(stepEntity);
        return step;
    }


    public SectionDto createSection(SectionDto newSection){
        InstructionSections section = new InstructionSections(newSection.getTitle());
        InstructionSections sectionEntity = (InstructionSections) genericDaoImpl.findByParametr(section.getTitle(),"InstructionSections",
                "title");
        if(sectionEntity!=null) {
            throw new BadCredentialsException("Section already exist");
        }
        genericDaoImpl.save(section);
        SectionDto sectionDto = new SectionDto();
        sectionDto.setTitle(section.getTitle());
        sectionDto.setId(section.getId());
        return sectionDto;
    }

    public String deleteSection(Object id){
        InstructionSections sections = (InstructionSections) genericDaoImpl.findById(new InstructionSections(), Long.parseLong(id.toString()));
        genericDaoImpl.del(sections);
        return "succes";
    }

    public InstructionAndTagsRequestDto updateInstruction(Long id, InstructionAndTagsRequestDto data){
        if(data.getRating() == null) {
            data.setRating(0);
        }

        InstructionEntity instructionEntity = (InstructionEntity)genericDaoImpl.findById(new InstructionEntity(), id);
        instructionEntity.setNameInstruction(data.getTitle());
        instructionEntity.setRating(data.getRating());
        instructionEntity.setUser((UserEntity)genericDaoImpl.findById(new UserEntity(), data.getUserId()));
        instructionEntity.setSections((InstructionSections)genericDaoImpl.findById(new InstructionSections(), data.getSection().getId()));

        InstructionSections instructionSections = (InstructionSections) genericDaoImpl.findById(new InstructionSections(), data.getSection().getId());
        instructionSections.setTitle(data.getSection().getTitle());

        genericDaoImpl.deleteList(genericDaoImpl.findListByParametr(instructionEntity,
                "TagsEntity", "instructionEntity"));

        if (data.getTags() != null) {
            for (Object tag : data.getTags()) {
                TagsEntity tagsEntity = new TagsEntity(tag.toString(), instructionEntity);
                genericDaoImpl.save(tagsEntity);
            }
        }

        for (StepDto step: data.getSteps()){
            updateStep(step.getId(), step);
        }
        return data;
    }

    public InstructionAndTagsRequestDto getInstruction(Object idInstruction){
        InstructionEntity instructionEntity = (InstructionEntity) genericDaoImpl.findById(new InstructionEntity(),
                Long.parseLong(idInstruction.toString()));
        SectionDto sectionDto = new SectionDto(instructionEntity.getSections().getId(),instructionEntity.getSections().getTitle());

        ArrayList<StepEntity> array = (ArrayList<StepEntity>) genericDaoImpl.findListByParametr(instructionEntity, "StepEntity", "instruction");
        ArrayList<StepDto> goodArray = new ArrayList<>();
        if(array == null){
            return null;
        }
        array.forEach( stepEntity -> goodArray.add(new StepDto(stepEntity.getIdStep(), stepEntity.getPosition(),
                stepEntity.getNameStep(), stepEntity.getText(), stepEntity.getImage(), stepEntity.getInstruction().getIdInstruction())));

        InstructionAndTagsRequestDto instructionAndTagsRequestDto = new InstructionAndTagsRequestDto(
                genericDaoImpl.findTagByInstruction(instructionEntity, "TagsEntity","instructionEntity"),
                goodArray,
                sectionDto,
                instructionEntity.getUser().getIdUser(),
                instructionEntity.getRating(), instructionEntity.getNameInstruction(),
                instructionEntity.getIdInstruction());
        return instructionAndTagsRequestDto;
    }

    public List<SectionDto> getSections(){
        return genericDaoImpl.list("InstructionSections");
    }

    public SectionDto getSection(long id){
        InstructionSections section = (InstructionSections)genericDaoImpl.findById(new InstructionSections(), id);
        return new SectionDto(section.getId(), section.getTitle());
    }

    public StepDto getStep(Long idStep){
        StepEntity stepEntity = (StepEntity)genericDaoImpl.findById(new StepEntity(), Long.parseLong(idStep.toString()));
                StepDto stepDto = new StepDto(stepEntity.getIdStep(), stepEntity.getPosition(),
                        stepEntity.getNameStep(), stepEntity.getText(), stepEntity.getImage(), stepEntity.getIdStep());
                return stepDto;
    }

    public String deleteStep(Object id){
        StepEntity stepEntity = (StepEntity)genericDaoImpl.findById(new StepEntity(), Long.parseLong(id.toString()));
        genericDaoImpl.del(stepEntity);
        return "succes";
    }

    public String deleteInstruction(Long id){
        InstructionEntity instructionEntity = (InstructionEntity)genericDaoImpl.findById(new InstructionEntity(), Long.parseLong(id.toString()));
        genericDaoImpl.deleteList(genericDaoImpl.findListByParametr(instructionEntity,
                "TagsEntity", "instructionEntity"));
        genericDaoImpl.deleteList((ArrayList<StepEntity>) genericDaoImpl.findListByParametr(instructionEntity, "StepEntity", "instruction"));
        genericDaoImpl.deleteList((ArrayList<CommentsEntity>)genericDaoImpl.findListByParametr(instructionEntity, "CommentsEntity","instructionEntity"));
        genericDaoImpl.del(instructionEntity);
        return "succes";
    }

    public void AddComment(CommentRequestDto commentRequestDto){
        InstructionEntity instructionEntity = (InstructionEntity)genericDaoImpl.findById(new InstructionEntity(),commentRequestDto.getIdInstruction());
        UserEntity userEntity = (UserEntity)genericDaoImpl.findById(new UserEntity(),commentRequestDto.getUser().getId());

        CommentsEntity commentsEntity = new CommentsEntity(commentRequestDto.getText(), instructionEntity,userEntity);
        genericDaoImpl.save(commentsEntity);
    }

    public ArrayList<CommentRequestDto> getCommentByInstruction(long id){
        InstructionEntity instructionEntity = genericDaoImpl.findById(new InstructionEntity(), id);
        CommentResponseDto commentsEntity = genericDaoImpl.findCommentByProject(instructionEntity, "CommentsEntity","instructionEntity");
        ArrayList<CommentRequestDto> commentRequestDtoArrayList = new ArrayList<>();
        if(commentsEntity.getComment() == null){
            return null;
        }
        for(int i = 0; i < commentsEntity.getComment().size(); i++) {
            CommentRequestDto commentrequestDto = new CommentRequestDto();

            UserDto userDto = new UserDto();
            userDto.setId(commentsEntity.getUser().get(i).getIdUser());
            userDto.setEmail(commentsEntity.getUser().get(i).getEmail());
            userDto.setFirstName(commentsEntity.getUser().get(i).getFirstName());
            userDto.setImage(commentsEntity.getUser().get(i).getImage());
            userDto.setLastName(commentsEntity.getUser().get(i).getLastName());
            userDto.setPassword(commentsEntity.getUser().get(i).getPassword());
            userDto.setUserName(commentsEntity.getUser().get(i).getUserName());
            commentrequestDto.setUser(userDto);

            long idComment = Long.parseLong(commentsEntity.getComment().get(i).toString());
            commentrequestDto.setText(((CommentsEntity)genericDaoImpl.findById(new CommentsEntity(), idComment)).getComment());
            commentrequestDto.setIdInstruction(id);
            commentrequestDto.setId(Integer.parseInt(commentsEntity.getComment().get(i).toString()));
            commentRequestDtoArrayList.add(commentrequestDto);
        }
        return commentRequestDtoArrayList;
    }

    public void updateCommentForInstruction(long id, CommentRequestDto commentRequestDto){
        CommentsEntity commentsEntity = (CommentsEntity)genericDaoImpl.findById(new CommentsEntity(), id);
        commentsEntity.setComment(commentRequestDto.getText());
        genericDaoImpl.save(commentsEntity);
    }

    public void deleteCom(Long id){
        CommentsEntity commentsEntity = (CommentsEntity)genericDaoImpl.findById(new CommentsEntity(), id);
        genericDaoImpl.del(commentsEntity);
    }

    public void addTags(InstructionEntity instructionEntity, List<Object> tags){
        for(Object tag:tags){
            TagsEntity tagsEntity = new TagsEntity(tag.toString(), instructionEntity);
            genericDaoImpl.save(tagsEntity);
        }

    }

    public List<InstructionAndTagsRequestDto> getUserInstructions(Object data){
        List<InstructionEntity> instructionEntity =  (List<InstructionEntity>)genericDaoImpl.findListByParametr((UserEntity)genericDaoImpl.findById(new UserEntity(),
                Long.parseLong(data.toString())),"InstructionEntity","user");
        if(instructionEntity == null) return null;
        List<InstructionAndTagsRequestDto> result = new ArrayList<>();
        instructionEntity.forEach(instruction -> result.add(new InstructionAndTagsRequestDto(
                genericDaoImpl.findTagByInstruction(instruction, "TagsEntity","instructionEntity"),
                null, new SectionDto(instruction.getSections().getId(),instruction.getSections().getTitle()),
                instruction.getUser().getIdUser(), instruction.getRating(), instruction.getNameInstruction(), instruction.getIdInstruction())));
        return result;
    }

    public List<InstructionAndTagsRequestDto> getUserInstructionsWithLimit(Object data, Integer limit){
        List<InstructionAndTagsRequestDto> result = getUserInstructions(data);
        if(result == null) return null;
        Collections.sort(result, new Comparator<InstructionAndTagsRequestDto>() {
            @Override
            public int compare(InstructionAndTagsRequestDto o1, InstructionAndTagsRequestDto o2) {
                return o1.getId() > o2.getId() ? -1 : o1.getId() < o2.getId() ? 1 : 0;
            }
        });
        if(limit <= result.size()) {
            ArrayList<InstructionAndTagsRequestDto> resultLimited = new ArrayList<>();
            for (int i = 0; i < limit; i++) {
                resultLimited.add(result.get(i));
            }
            return resultLimited;
        }else{
            return result;
        }
    }

    public List<InstructionAndTagsRequestDto> getInstructionsBySections(Object data){
        InstructionSections instructionSections = (InstructionSections)genericDaoImpl.findById(new InstructionSections(), Long.parseLong(data.toString()));
        List<InstructionEntity> instructionEntity = (List<InstructionEntity>)genericDaoImpl.findListByParametr(instructionSections, "InstructionEntity","sections");
        if(instructionEntity == null) return null;
        List<InstructionAndTagsRequestDto> result = new ArrayList<>();
        instructionEntity.forEach(instruction -> result.add(new InstructionAndTagsRequestDto(
                genericDaoImpl.findTagByInstruction(instruction, "TagsEntity","instructionEntity"),
                null, new SectionDto(instruction.getSections().getId(),instruction.getSections().getTitle()),
                instruction.getUser().getIdUser(), instruction.getRating(), instruction.getNameInstruction(), instruction.getIdInstruction())));
        return result;
    }

    public String setRating(RatingRequestDto rating){
        InstructionEntity currentProject = (InstructionEntity)genericDaoImpl.findById
                (new InstructionEntity(),Long.parseLong(rating.getInstructionId().toString()));
        UserEntity userEntity = (UserEntity) genericDaoImpl.findById
                (new UserEntity(),Long.parseLong(rating.getUserId().toString()));
        RatingEntity ratingEntity = (RatingEntity)genericDaoImpl.findByTwoParametr(currentProject,
                "RatingEntity","instructionEntity","userEntite", userEntity);
        if(ratingEntity==null) {
            Double newRatingValue = 0.0;
            Integer firstStarCnt = 0;
            Integer secondStarCnt = 0;
            Integer thirdStarCnt = 0;
            Integer fourthStarCnt = 0;
            Integer fifthStarCnt = 0;
            switch(rating.getValue().intValue()){
                case 1:{
                    firstStarCnt++;
                    break;
                }
                case 2:{
                    secondStarCnt++;
                    break;
                }
                case 3:{
                    thirdStarCnt++;
                    break;
                }
                case 4:{
                    fourthStarCnt++;
                    break;
                }
                case 5:{
                    fifthStarCnt++;
                    break;
                }
            }
            genericDaoImpl.update("InstructionEntity", "idInstruction", currentProject.getIdInstruction(),
                        "rating", newRatingValue);
            genericDaoImpl.save(new RatingEntity(currentProject, userEntity, newRatingValue, firstStarCnt, secondStarCnt,
                    thirdStarCnt, fourthStarCnt, fifthStarCnt));
            return "success";
        }
        return "You already gave rating";
    }

    public int getRating(Long id){
        InstructionEntity instructionEntity = (InstructionEntity)genericDaoImpl.findById(new InstructionEntity(), id);
        ArrayList<RatingEntity> ratingEntities = (ArrayList<RatingEntity>) genericDaoImpl.findListByParametr(instructionEntity,
                "RatingEntity", "instructionEntity");
        Integer firstStarCnt = 0;
        Integer secondStarCnt = 0;
        Integer thirdStarCnt = 0;
        Integer fourthStarCnt = 0;
        Integer fifthStarCnt = 0;
        if(ratingEntities == null){
            return 0;
        }
        for(int i = 0; i < ratingEntities.size(); i++){
            if(ratingEntities.get(i).getFirstStarCnt() > 0){
                firstStarCnt += ratingEntities.get(i).getFirstStarCnt();
            }
            if(ratingEntities.get(i).getSecondStarCnt() > 0){
                secondStarCnt += ratingEntities.get(i).getSecondStarCnt();
            }
            if(ratingEntities.get(i).getThirdStarCnt() > 0){
                thirdStarCnt += ratingEntities.get(i).getThirdStarCnt();
            }
            if(ratingEntities.get(i).getFourthStarCnt() > 0){
                fourthStarCnt += ratingEntities.get(i).getFourthStarCnt();
            }
            if(ratingEntities.get(i).getFifthStarCnt() > 0){
                fifthStarCnt += ratingEntities.get(i).getFifthStarCnt();
            }
        }

        float result = Math.round(((firstStarCnt + 2 * secondStarCnt + 3 * thirdStarCnt + 4 * fourthStarCnt + 5 * fifthStarCnt) /ratingEntities.size()));
        return (int)result;
    }

    public List<InstructionEntity> searcheByTag(Object tag){
        return genericDaoImpl.findProjectByTag(tag.toString(),"TagsEntity","name");
    }
}
