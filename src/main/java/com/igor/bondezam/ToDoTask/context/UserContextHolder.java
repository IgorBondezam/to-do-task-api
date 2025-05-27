package com.igor.bondezam.ToDoTask.context;

import com.igor.bondezam.ToDoTask.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public class UserContextHolder {

    public static User getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(Objects.isNull(auth) || Objects.isNull(auth.getPrincipal()) || !(auth.getPrincipal() instanceof User)) {
            return null;
        }
        return (User) auth.getPrincipal();
    }

    public static Long getAuthenticatedUserId() {
        User userDetails = getUser();
        if(Objects.isNull(userDetails)) {
            return null;
        }
        return userDetails.getId();
    }
}
