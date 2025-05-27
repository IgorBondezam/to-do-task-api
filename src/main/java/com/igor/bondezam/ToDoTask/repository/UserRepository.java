package com.igor.bondezam.ToDoTask.repository;

import com.igor.bondezam.ToDoTask.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByEmail(String email);
}
