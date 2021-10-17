package com.example.travelproject.controller;

import com.example.travelproject.entity.User;
import com.example.travelproject.service.UserProfileService;
import com.example.travelproject.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;


@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private static Logger log = LoggerFactory.getLogger(UserController.class);

    private UserService userService;
    private UserProfileService userProfileService;


    @GetMapping("/registration")
    private ModelAndView getRegistrationPage(ModelAndView modelAndView){
        modelAndView.addObject("newUser", new User());
        modelAndView.addObject("usernameIsExist", false);
        modelAndView.addObject("emailIsExist", false);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @PostMapping("/registration")
    private ModelAndView register(@Valid @ModelAttribute(name = "newUser") User newUser, BindingResult result){
        ModelAndView modelAndView = new ModelAndView();
        if(result.hasErrors()){
            modelAndView.setViewName("registration");
            log.error("Validation error");
            return modelAndView;
        }
        if(userService.existsUserByUsername(newUser.getUsername())){
            log.info("Registration process started");
            modelAndView.addObject("usernameIsExist", true);
            modelAndView.setViewName("registration");
            log.warn("User '"+ newUser.getUsername() + "' already exist");
        } else {
            if(userService.existUserByEmail(newUser.getEmail())){
                modelAndView.addObject("emailIsExist", true);
                modelAndView.setViewName("registration");
                log.warn("Email '"+ newUser.getEmail() + "' already exist");
            } else{
                userService.save(newUser);
                log.info("User '"+ newUser.getUsername() + "' created");
                userProfileService.save(newUser);
                log.info("Profile for '"+ newUser.getUsername() + "' created");
                modelAndView.addObject("authUser", new User());
                modelAndView.setViewName("authorization");
                log.info("Registration process completed successfully");
            }
        }
        return modelAndView;
    }

    @GetMapping("/authorization")
    public ModelAndView getAuthorizationPage(ModelAndView modelAndView){
        modelAndView.addObject("authUser", new User());
        modelAndView.addObject("loginError", false);
        modelAndView.setViewName("authorization");
        return modelAndView;
    }

    @PostMapping("/authorization")
    public ModelAndView login(@ModelAttribute(name = "authUser") User authUser, HttpSession session){
        log.info("Authorization process started");
        ModelAndView modelAndView = new ModelAndView();
        Optional<User> userByUsernameAndPassword = userService.findUserByUsernameAndPassword(authUser);
        if(userByUsernameAndPassword.isEmpty()){
            modelAndView.addObject("loginError", true);
            modelAndView.setViewName("authorization");
            log.error("Wrong username or password");
            return modelAndView;
        } else {
            User user = userByUsernameAndPassword.get();
            session.setAttribute("user", user);
            modelAndView.setViewName("redirect:/home");
            log.info("User '"+ user.getUsername() + "' logged in");
        }
        log.info("Authorization process completed successfully");
        return modelAndView;
    }

    @GetMapping("logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        log.info("User log out");
        return "redirect:/home";
    }
}
