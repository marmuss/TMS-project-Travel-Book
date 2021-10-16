package com.example.travelproject.controller;

import com.example.travelproject.service.PostService;
import com.example.travelproject.service.UserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
@RequestMapping("/home")
public class HomeController {
    private PostService postService;
    private UserProfileService userProfileService;

    @GetMapping
    public ModelAndView getHomePage(ModelAndView modelAndView){
        modelAndView.addObject("lastThreePosts", postService.getLastThree());
        modelAndView.addObject("lastTreeUsers", userProfileService.getLastUsers());

        modelAndView.setViewName("home");
        return modelAndView;
    }
}
