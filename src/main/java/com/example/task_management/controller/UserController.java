package com.example.task_management.controller;

import com.example.task_management.dto.LoginUserDto;
import com.example.task_management.dto.RegUserDto;
import com.example.task_management.entity.SessionUser;
import com.example.task_management.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/reg")
    public String reg(Model model){
        model.addAttribute("newUser", new RegUserDto());
        return "reg";
    }

    @PostMapping("/reg")
    public String reg(@ModelAttribute("newUser") @Valid RegUserDto regUserDto,
                      BindingResult bindingResult,
                      Model model){
        if (bindingResult.hasErrors()){
            return "reg";
        }
        try {
            userService.save(regUserDto);
        } catch (ConstraintViolationException e){
            model.addAttribute("regError", "The user with this username and email already exists");
            return "reg";
        }
        return "redirect:/user/login";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("newLoginUser", new LoginUserDto());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("newLoginUser") @Valid LoginUserDto loginUserDto,
                        BindingResult bindingResult,
                        HttpSession httpSession,
                        Model model){
        if (bindingResult.hasErrors()){
            return "login";
        }
        Optional<SessionUser> sessionUser = userService.login(loginUserDto);
        if (sessionUser.isPresent()){
            httpSession.setAttribute("sessionUser", sessionUser.get());

            return "redirect:/goals";
        } else {
            model.addAttribute("loginInvalid", "Email or password is invalid, please try again");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/user/login";
    }

}
