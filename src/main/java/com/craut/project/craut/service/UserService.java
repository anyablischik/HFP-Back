package com.craut.project.craut.service;

import com.craut.project.craut.model.*;
import com.craut.project.craut.repository.GenericDaoImpl;
import com.craut.project.craut.security.model.TokenPayload;
import com.craut.project.craut.security.service.AuthenticationHelper;
import com.craut.project.craut.service.dto.MessageRequestDto;
import com.craut.project.craut.service.dto.ProjectRequestDto;
import com.craut.project.craut.service.dto.RegistrtionRequestDto;
import com.craut.project.craut.service.dto.UserListDto;
import com.craut.project.craut.service.transformer.UserListTransformer;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ikatlinsky
 * @since 5/12/17
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    @Autowired
    GenericDaoImpl genericDaoImpl;

    private final AuthenticationHelper authenticationHelper;

    public String update(final RegistrtionRequestDto registrtionRequestDto, String token) {
        if(token.contains(""))
            token =  token.split(" ")[1];
        TokenPayload tokenPayload = authenticationHelper.decodeToken(token);
        Long userRoleEntityId = tokenPayload.getUserId();
        UserRoleEntity userRoleEntity = (UserRoleEntity) genericDaoImpl.findById(new UserRoleEntity(),
                userRoleEntityId);
        userRoleEntity.getUser().setImage(registrtionRequestDto.getImage());
        userRoleEntity.getUser().setFirstName(registrtionRequestDto.getFirstName());
        userRoleEntity.getUser().setLastName(registrtionRequestDto.getLastName());
        userRoleEntity.getUser().setEmail(registrtionRequestDto.getEmail());
        userRoleEntity.getUser().setPassword(registrtionRequestDto.getPassword());
        userRoleEntity.getUser().setUserName(registrtionRequestDto.getUserName());
        genericDaoImpl.save(userRoleEntity);
        return "sucess";
    }


    @Transactional(readOnly = true)
    public List<UserListDto> findAll() {
        return genericDaoImpl.list("UserEntity");
    }


    @Transactional(readOnly = true)
    public List<MessageRequestDto> findMessage() {
        return genericDaoImpl.list("MessageEntity");
    }

    public String blockUser(ArrayList<Long> blockRequestDto,String token){
        Long choose = blockRequestDto.get(0);
        blockRequestDto.remove(0);
        for(Long block:blockRequestDto)
        {
            if(choose == 0)
                genericDaoImpl.update("UserEntity","iduser",block,"blocked",3);
            if(choose == 1 )
                genericDaoImpl.update("UserEntity","iduser",block,"blocked",1);
            if(choose == 2) {
                UserEntity user = (UserEntity)genericDaoImpl.findById(new UserEntity(),block);
                genericDaoImpl.deleteList(genericDaoImpl.findListByParametr(user,"CommentsEntity","userEntity"));
                UserRoleEntity roleEntity = (UserRoleEntity)genericDaoImpl.findByParametr(user,"UserRoleEntity","user");
                List<ProjectEntity> list= (List<ProjectEntity>)genericDaoImpl.findListByParametr(user,"ProjectEntity","user");
                if(list != null) {
                    for (ProjectEntity obj : list) {
                        genericDaoImpl.deleteList(genericDaoImpl.findListByParametr(obj, "CommentsEntity", "projectEntity"));
                        genericDaoImpl.deleteList(genericDaoImpl.findListByParametr(obj, "TagsEntity", "projectEntity"));
                        genericDaoImpl.del(obj);
                    }
                }
                genericDaoImpl.del(roleEntity);
            }
        }
        return "success";
    }

    public String confirmUser(ArrayList<Long> blockRequestDto){
        Long choose = blockRequestDto.get(0);
        blockRequestDto.remove(0);
        MessageEntity messageEntity = (MessageEntity)genericDaoImpl.findById(new MessageEntity(),blockRequestDto.get(0));
        UserEntity User = (UserEntity) genericDaoImpl.findById(new UserEntity(),messageEntity.getUser().getIduser());
            if(choose == 0)
                genericDaoImpl.update("UserRoleEntity","user",User.getIduser(),"role",3);
            genericDaoImpl.del(messageEntity);
        return "success";
    }
    public String confirmUser(MessageRequestDto confirm, String token) {
        TokenPayload tokenPayload = authenticationHelper.decodeToken(token);
        Long userEntityId = tokenPayload.getUserId();
        UserEntity user = (UserEntity)genericDaoImpl.findById(new UserEntity(),userEntityId);
        MessageEntity messageEntity = new MessageEntity(confirm.getText(),"потверждение ящика",
                confirm.getImage(),user);
        genericDaoImpl.save(messageEntity);
        return "success";
    }
    public List<ProjectEntity> fullTextSearch(String search)
    {
        List <ProjectEntity> list = new ArrayList<>();
        list = genericDaoImpl.fullTextSearch(search,"project","name,purpose,content", 0, list);
        list = genericDaoImpl.fullTextSearch(search,"comments","comment", 2,list);
        list = genericDaoImpl.fullTextSearch(search,"tags","name", 2,list);

        return list;
    }
}
