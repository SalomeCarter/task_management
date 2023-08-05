package com.example.task_management.mapper;

import com.example.task_management.entity.SessionUser;
import com.example.task_management.entity.User;

public class UserMapper {
    public static SessionUser userToSessionUser(User user){
        SessionUser sessionUser = new SessionUser();
        sessionUser.setId(user.getId());
        sessionUser.setName(user.getName());
        sessionUser.setUsername(user.getUsername());
        sessionUser.setEmail(user.getEmail());
        return sessionUser;
    }
}
