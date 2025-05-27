package com.igor.bondezam.ToDoTask.converter;

import com.igor.bondezam.ToDoTask.domain.User;
import com.igor.bondezam.ToDoTask.dto.req.UserReq;
import com.igor.bondezam.ToDoTask.dto.res.UserRes;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserConverter {

    public User reqToEntity(UserReq userReq) {
        User user = new User();
        user.setName(userReq.getName());
        user.setBirthdayDate(userReq.getBirthdayDate());
        user.setCpf(userReq.getCpf());
        user.setEmail(userReq.getEmail());
        user.setHeight(userReq.getHeight());
        user.setWeight(userReq.getWeight());
        return user;
    }

    public User resToEntity(UserRes userRes) {
        User user = new User();
        user.setName(userRes.getName());
        user.setAge(userRes.getAge());
        user.setBirthdayDate(userRes.getBirthdayDate());
        user.setCpf(userRes.getCpf());
        user.setEmail(userRes.getEmail());
        user.setHeight(userRes.getHeight());
        user.setWeight(userRes.getWeight());
        return user;
    }

    public UserReq entityToReq(User entity) {
        UserReq userReq = new UserReq();
        userReq.setName(entity.getName());
        userReq.setBirthdayDate(entity.getBirthdayDate());
        userReq.setCpf(entity.getCpf());
        userReq.setEmail(entity.getEmail());
        userReq.setHeight(entity.getHeight());
        userReq.setWeight(entity.getWeight());
        return userReq;
    }

    public UserRes entityToRes(User entity) {
        if(Objects.isNull(entity)){
            return null;
        }
        UserRes userRes = new UserRes();
        userRes.setName(entity.getName());
        userRes.setAge(entity.getAge());
        userRes.setBirthdayDate(entity.getBirthdayDate());
        userRes.setCpf(entity.getCpf());
        userRes.setEmail(entity.getEmail());
        userRes.setHeight(entity.getHeight());
        userRes.setWeight(entity.getWeight());
        return userRes;
    }
}
