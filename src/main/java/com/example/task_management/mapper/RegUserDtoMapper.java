package com.example.task_management.mapper;

import com.example.task_management.dto.RegUserDto;
import com.example.task_management.entity.User;

public class RegUserDtoMapper {
    public static User regUserToUser(RegUserDto regUserDto){
        User user = new User();
        user.setName(regUserDto.getName());
        user.setUsername(regUserDto.getUsername());
        user.setEmail(regUserDto.getEmail());
        user.setPassword(regUserDto.getPassword());
        return user;
    }
}
