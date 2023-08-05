package com.example.task_management.service;

import com.example.task_management.dto.LoginUserDto;
import com.example.task_management.entity.User;
import com.example.task_management.entity.SessionUser;
import com.example.task_management.dto.RegUserDto;
import com.example.task_management.mapper.RegUserDtoMapper;
import com.example.task_management.mapper.UserMapper;
import com.example.task_management.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void save(RegUserDto regUserDto) {
        User user = RegUserDtoMapper.regUserToUser(regUserDto);
        userRepository.save(user);
    }

    public Optional<SessionUser> login(LoginUserDto loginUserDto) {
        Optional<User> user = userRepository.findByEmail(loginUserDto.getEmail());
        if (user.isPresent()) {
            User currentUser = user.get();
            if (currentUser.getPassword().equals(loginUserDto.getPassword())) {
                return Optional.of(UserMapper.userToSessionUser(currentUser));
            }
        }
        return Optional.empty();
    }




}
