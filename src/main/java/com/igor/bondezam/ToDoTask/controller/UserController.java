package com.igor.bondezam.ToDoTask.controller;

import com.igor.bondezam.ToDoTask.converter.UserConverter;
import com.igor.bondezam.ToDoTask.domain.User;
import com.igor.bondezam.ToDoTask.dto.req.UserReq;
import com.igor.bondezam.ToDoTask.dto.res.UserRes;
import com.igor.bondezam.ToDoTask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private UserConverter converter;

    @GetMapping
    public ResponseEntity<List<UserRes>> findAll(){
        return ResponseEntity.ok(service.findAll().stream().map(
                converter::entityToRes).collect(Collectors.toList()));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserRes> findById(@PathVariable("id") Long id){
        return ResponseEntity.ok(converter.entityToRes(service.findById(id)));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserRes> updateUser(@PathVariable("id") Long id, @RequestBody UserReq req){
        return ResponseEntity.ok(converter.entityToRes(service.createOrUpdateEntity(id, converter.reqToEntity(req))));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id){
        service.deleteEntity(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<UserRes> createUser(@RequestBody UserReq userReq) {
        User user = converter.reqToEntity(userReq);
        if(Objects.nonNull(service.findByEmail(user.getEmail()))) {
            throw new RuntimeException("This email user is alrealy used");
        }
        return ResponseEntity.status(201).body(converter.entityToRes(service.createOrUpdateEntity(user)));
    }

    private String passwordEncoder(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
