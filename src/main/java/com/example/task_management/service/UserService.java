package com.example.task_management.service;

import com.example.task_management.dto.LoginUserDto;
import com.example.task_management.entity.User;
import com.example.task_management.entity.SessionUser;
import com.example.task_management.dto.RegUserDto;
import com.example.task_management.mapper.RegUserDtoMapper;
import com.example.task_management.mapper.UserMapper;
import com.example.task_management.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Transactional
@Service
public class UserService {
    private static final Logger log = Logger.getLogger(UserService.class.getName());

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public void save(RegUserDto regUserDto) {
        User user = RegUserDtoMapper.regUserToUser(regUserDto);
        user.setPassword(passwordEncoder.encode(regUserDto.getPassword()));
        userRepository.save(user);
        log.log(Level.INFO, "User saved " + regUserDto.getUsername());
    }

    public Optional<SessionUser> login(LoginUserDto loginUserDto) {
        Optional<User> user = userRepository.findByEmail(loginUserDto.getEmail());
        if (user.isPresent()) {
            User currentUser = user.get();
            if (currentUser.getPassword().equals(loginUserDto.getPassword())) {
                SessionUser sessionUser = UserMapper.userToSessionUser(currentUser);
                log.log(Level.INFO, "Login successful for user " + loginUserDto.getEmail());
                return Optional.of(sessionUser);
            }
        }
        log.log(Level.INFO, "Login failed for user " + loginUserDto.getEmail());
        return Optional.empty();
    }
}

