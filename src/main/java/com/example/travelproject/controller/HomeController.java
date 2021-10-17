package com.example.travelproject.controller;

import com.example.travelproject.entity.Role;
import com.example.travelproject.entity.User;
import com.example.travelproject.service.CountryService;
import com.example.travelproject.service.PostService;
import com.example.travelproject.service.UserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
@RequestMapping("/home")
@SessionAttributes("user")
public class HomeController {
    private PostService postService;
    private UserProfileService userProfileService;
    private CountryService countryService;

    @GetMapping
    public ModelAndView getHomePage(ModelAndView modelAndView, HttpSession session){
         if (session.getAttribute("user") != null){
             User user = (User) session.getAttribute("user");
             if(user.getRole().equals(Role.ADMIN)){
                 modelAndView.addObject("admin", true);
             }
         }
        modelAndView.addObject("lastThreePosts", postService.getLastThree());
        modelAndView.addObject("lastSavedUsers", userProfileService.getLastUsers());
        modelAndView.addObject("mostPopularCountry", countryService.getTopByTourists().getCountryName());
        modelAndView.setViewName("home");
        return modelAndView;
    }
}
