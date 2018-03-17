package com.craut.project.craut.service;

import com.craut.project.craut.model.*;
import com.craut.project.craut.repository.GenericDaoImpl;
import com.craut.project.craut.security.model.TokenPayload;
import com.craut.project.craut.security.service.AuthenticationHelper;
import com.craut.project.craut.service.dto.*;
import com.craut.project.craut.service.transformer.AuthUserTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    @Autowired
    GenericDaoImpl genericDaoImpl;
    private final AuthUserTransformer authUserTransformer;
    private final AuthenticationHelper authenticationHelper;
    private  final PasswordEncoder passwordEncoder;

    public AuthUserDto update(final RegistrtionRequestDto registrtionRequestDto, String token) {
        if(token.contains(""))
            token =  token.split(" ")[1];
        TokenPayload tokenPayload = authenticationHelper.decodeToken(token);
        Long userEntityId = tokenPayload.getUserId();
        UserEntity userEntity = (UserEntity)genericDaoImpl.findById(new UserEntity(),userEntityId);
        UserRoleEntity userRoleEntity = (UserRoleEntity) genericDaoImpl.findByParametr(userEntity,
                "UserRoleEntity","user");
        UserEntity user = (UserEntity) genericDaoImpl.findByParametr(registrtionRequestDto.getUserName(),"UserEntity",
                "userName");
        if(user != null  && user.getUserName().equals(registrtionRequestDto.getUserName()) && user.getIdUser() != userEntity.getIdUser()) {
            throw new BadCredentialsException("Login not unique.");
        }
        userRoleEntity.getUser().setImage(registrtionRequestDto.getImage());
        userRoleEntity.getUser().setFirstName(registrtionRequestDto.getFirstName());
        userRoleEntity.getUser().setLastName(registrtionRequestDto.getLastName());
        userRoleEntity.getUser().setEmail(registrtionRequestDto.getEmail());
        if (!registrtionRequestDto.getPassword().contains(userRoleEntity.getUser().getPassword())) {
            String password = passwordEncoder.encode(Optional.ofNullable(registrtionRequestDto.getPassword())
                    .orElseThrow(() -> new BadCredentialsException("Password should be passed.")));
            userRoleEntity.getUser().setPassword(password);
        }
        else {
            userRoleEntity.getUser().setPassword(registrtionRequestDto.getPassword());
        }
        userRoleEntity.getUser().setUserName(registrtionRequestDto.getUserName());
        genericDaoImpl.save(userRoleEntity);
        return authUserTransformer.makeDto(userRoleEntity);
    }

    @Transactional(readOnly = true)
    public List<AuthUserDto> findAll() {
        List<UserRoleEntity> listDto = (List<UserRoleEntity>)genericDaoImpl.list("UserRoleEntity");
        List<AuthUserDto> list = new ArrayList<>();
        for(UserRoleEntity userListDto: listDto){
            if(userListDto.getRole().getRoleStatus() != "ROLE_ADMIN"){
                list.add(authUserTransformer.makeDto(userListDto));
            }
        }
        return list;
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
                genericDaoImpl.update("UserEntity","iduser",block,
                        "blocked",3);
            if(choose == 1 )
                genericDaoImpl.update("UserEntity","iduser",block,
                        "blocked",1);
            if(choose == 2) {
                UserEntity user = (UserEntity)genericDaoImpl.findById(new UserEntity(),block);
                genericDaoImpl.deleteList(genericDaoImpl.findListByParametr(user,"CommentsEntity",
                        "userEntity"));
                UserRoleEntity roleEntity = (UserRoleEntity)genericDaoImpl.findByParametr(user,
                        "UserRoleEntity","user");
                List<InstructionEntity> list= (List<InstructionEntity>)genericDaoImpl.findListByParametr(user,
                        "InstructionEntity","user");
                if(list != null) {
                    for (InstructionEntity obj : list) {
                        genericDaoImpl.deleteList(genericDaoImpl.findListByParametr(obj,
                                "CommentsEntity", "instructionEntity"));
                        genericDaoImpl.deleteList(genericDaoImpl.findListByParametr(obj,
                                "TagsEntity", "instructionEntity"));
                        genericDaoImpl.del(obj);
                    }
                }
                genericDaoImpl.del(roleEntity);
            }
        }
        return "success";
    }

    public String deleteUser(Long id){
        UserEntity user = (UserEntity)genericDaoImpl.findById(new UserEntity(),id);
        genericDaoImpl.deleteList(genericDaoImpl.findListByParametr(user,"CommentsEntity",
                "userEntity"));
        UserRoleEntity roleEntity = (UserRoleEntity)genericDaoImpl.findByParametr(user,
                "UserRoleEntity","user");
        List<InstructionEntity> list= (List<InstructionEntity>)genericDaoImpl.findListByParametr(user,
                "InstructionEntity","user");
        if(list != null) {
            for (InstructionEntity instructionEntity : list) {
                genericDaoImpl.deleteList(genericDaoImpl.findListByParametr(instructionEntity,
                        "TagsEntity", "instructionEntity"));
                genericDaoImpl.deleteList((ArrayList<StepEntity>) genericDaoImpl.findListByParametr(instructionEntity, "StepEntity", "instruction"));
                genericDaoImpl.del(instructionEntity);
            }
        }
        genericDaoImpl.del(roleEntity);
        return "success";
    }

    public String confirmUser(ArrayList<Long> blockRequestDto){
        Long choose = blockRequestDto.get(0);
        blockRequestDto.remove(0);
        MessageEntity messageEntity = (MessageEntity)genericDaoImpl.findById(new MessageEntity(),blockRequestDto.get(0));
        UserEntity User = (UserEntity) genericDaoImpl.findById(new UserEntity(),messageEntity.getUser().getIdUser());
            if(choose == 0)
                genericDaoImpl.update("UserRoleEntity","user",User.getIdUser(),"role",
                        3);
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
    public List<InstructionEntity> fullTextSearch(String search)
    {
        List <InstructionEntity> list = new ArrayList<>();
        list = genericDaoImpl.fullTextSearch(search,"project","name,purpose,content", 0, list);
        list = genericDaoImpl.fullTextSearch(search,"comments","comment", 2,list);
        list = genericDaoImpl.fullTextSearch(search,"tags","name", 2,list);

        return list;
    }
}
