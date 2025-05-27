package com.igor.bondezam.ToDoTask.service;

import com.igor.bondezam.ToDoTask.domain.User;
import com.igor.bondezam.ToDoTask.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> findAll(){
        return repository.findAll();
    }

    public User findById(long id){
        return repository.findById(id).orElse(null);
    }

    public User findByEmail(String email){
        return repository.findUserByEmail(email);
    }

    public User createOrUpdateEntity(Long id, User entity){
        entity.setId(id);
        return createOrUpdateEntity(entity);
    }

    public User createOrUpdateEntity(User entity){
        return repository.save(entity);
    }
    public void deleteEntity(Long id){
        repository.deleteById(id);
    }
}
