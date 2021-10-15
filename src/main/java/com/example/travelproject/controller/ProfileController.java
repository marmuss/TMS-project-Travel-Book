package com.example.travelproject.controller;

import com.example.travelproject.entity.User;
import com.example.travelproject.entity.UserProfile;
import com.example.travelproject.repository.UserRepository;
import com.example.travelproject.service.PostService;
import com.example.travelproject.service.UserProfileService;
import com.example.travelproject.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/profile")
@SessionAttributes("user")
@AllArgsConstructor
public class ProfileController {

    private UserProfileService userProfileService;
    private PostService postService;


    @GetMapping()
    public ModelAndView getProfilePage(HttpSession session, ModelAndView modelAndView){
        User user = (User) session.getAttribute("user");
        if(userProfileService.findUserProfileByUser(user).isPresent()){
            modelAndView.addObject("profileIsExist", true);
            modelAndView.addObject("profile",userProfileService.findUserProfileByUser(user).get());
            modelAndView.addObject("posts", postService.getAllByUser(user));
        } else{
            modelAndView.addObject("profileIsExist", false);
        }
        modelAndView.setViewName("profile");
        return modelAndView;
    }

    @GetMapping("/{id}")
    private ModelAndView getUserPage(@PathVariable(name = "id") String id){
        ModelAndView modelAndView = new ModelAndView();
        Optional<UserProfile> userProfile = userProfileService.findUserProfileByUserId(Long.valueOf(id));
        if(userProfile.isPresent()){
            modelAndView.addObject("profileIsExist", true);
            modelAndView.addObject("profile",userProfile.get());
            modelAndView.addObject("posts", postService.getAllByUserId(Long.parseLong(id)));
        } else{
        modelAndView.addObject("profileIsExist", false);
    }
        modelAndView.setViewName("profile");
        return modelAndView;
    }

    @GetMapping("/addCompanion/{id}")
    private String addCompanion(@PathVariable(name = "id") String id, HttpSession session){
        User user = (User) session.getAttribute("user");
        UserProfile profile = userProfileService.findUserProfileByUser(user).get();
        userProfileService.addCompanion(Long.parseLong(id), profile);
        return "redirect:/profile/{id}";
    }
}
