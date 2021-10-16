package com.example.travelproject.controller;

import com.example.travelproject.entity.User;
import com.example.travelproject.service.UserProfileService;
import com.example.travelproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Optional;


@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    private UserProfileService userProfileService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    private ModelAndView getRegistrationPage(ModelAndView modelAndView){
        modelAndView.addObject("newUser", new User());
        modelAndView.addObject("usernameIsExist", false);
        modelAndView.addObject("emailIsExist", false);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @PostMapping("/registration")
    private ModelAndView register(@ModelAttribute(name = "newUser") User newUser){
        ModelAndView modelAndView = new ModelAndView();
        if(userService.existsUserByUsername(newUser.getUsername())){
            modelAndView.addObject("usernameIsExist", true);
            modelAndView.setViewName("/registration");
        } else {
            if(userService.existUserByEmail(newUser.getEmail())){
                modelAndView.addObject("emailIsExist", true);
                modelAndView.setViewName("/registration");
            } else{
                userService.save(newUser);
                userProfileService.save(newUser);
                modelAndView.addObject("authUser", new User());
                modelAndView.setViewName("/authorization");
            }
        }
        return modelAndView;
    }

    @GetMapping("/authorization")
    public ModelAndView getAuthorizationPage(ModelAndView modelAndView){
        modelAndView.addObject("authUser", new User());
        modelAndView.addObject("loginError", false);
        modelAndView.setViewName("/authorization");
        return modelAndView;
    }

    @PostMapping("/authorization")
    public ModelAndView login(@ModelAttribute(name = "authUser") User authUser, HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        Optional<User> userByUsernameAndPassword = userService.findUserByUsernameAndPassword(authUser);
        if(userByUsernameAndPassword.isEmpty()){
            modelAndView.addObject("loginError", true);
            modelAndView.setViewName("/authorization");
        } else {
            User user = userByUsernameAndPassword.get();
            session.setAttribute("user", user);
            modelAndView.setViewName("/home");
        }
        return modelAndView;
    }

    @GetMapping("logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "home";
    }
}
