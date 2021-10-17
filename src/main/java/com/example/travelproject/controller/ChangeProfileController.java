package com.example.travelproject.controller;

import com.example.travelproject.entity.Country;
import com.example.travelproject.entity.User;
import com.example.travelproject.entity.UserProfile;
import com.example.travelproject.service.CountryService;
import com.example.travelproject.service.UserProfileService;
import com.example.travelproject.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/changeProfile")
public class ChangeProfileController {

    private UserService userService;
    private UserProfileService userProfileService;
    private CountryService countryService;


    @GetMapping()
    public ModelAndView getChangeProfilePage(ModelAndView modelAndView, HttpSession session){
        User user =(User) session.getAttribute("user");
        modelAndView.addObject("usernameIsExist", false);
        modelAndView.addObject("emailIsExist", false);
        modelAndView.addObject("emailsNotMatch", false);
        modelAndView.addObject("wrongPassword", false);
        modelAndView.addObject("passwordsNotMatch", false);
        modelAndView.addObject("countryList", countryService.getAll());
        modelAndView.addObject("visitedCountries", userProfileService.findUserProfileByUser(user).get().getVisitedCountries());
        modelAndView.setViewName("changeProfile");

        return modelAndView;
    }


    @PostMapping("/changeName")
    public ModelAndView  changeName(@RequestParam("newName") String newName, HttpSession session, ModelAndView modelAndView){
        User user =(User) session.getAttribute("user");
        User changedUser = userService.changeName(user, newName);
        session.setAttribute("user", changedUser);
        modelAndView.setViewName("changeProfile");
        return modelAndView;
    }

    @PostMapping("/changeUsername")
    public ModelAndView  changeUsername(@RequestParam("newUsername") String newUsername, HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        User user =(User) session.getAttribute("user");
        if(userService.existsUserByUsername(newUsername)){
            modelAndView.addObject("usernameIsExist", true);
        } else {
            User changedUser = userService.changeUsername(user, newUsername);
            session.setAttribute("user", changedUser);
        }
        modelAndView.setViewName("changeProfile");
        return modelAndView;
    }

    @PostMapping("/changeEmail")
    public ModelAndView changeEmail(@RequestParam("newEmail") String newEmail, @RequestParam("confirmEmail") String confirmEmail,
                                    HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        User user =(User) session.getAttribute("user");
        if(newEmail.equals(confirmEmail)){
            if (userService.existUserByEmail(newEmail)){
                modelAndView.addObject("emailIsExist", true);
            } else {
                User changedUser = userService.changeEmail(user, newEmail);
                session.setAttribute("user", changedUser);
            }
        } else {
            modelAndView.addObject("emailsNotMatch", true);
        }
        modelAndView.setViewName("changeProfile");
        return modelAndView;
    }

    @PostMapping("/changePassword")
    public ModelAndView changePassword(@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword,
                                       @RequestParam("confirmNewPassword") String confirmNewPassword, HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        User user =(User) session.getAttribute("user");
        if (user.getPassword().equals(oldPassword)){
            if(newPassword.equals(confirmNewPassword)){
                User changedUser = userService.changePassword(user, newPassword);
                session.setAttribute("user", changedUser);
            } else {
                modelAndView.addObject("passwordsNotMatch", true);
            }
        } else {
            modelAndView.addObject("wrongPassword", true);
        }
        modelAndView.setViewName("changeProfile");
        return modelAndView;
    }

    @PostMapping("/uploadPhoto")
    public ModelAndView uploadPhoto (@RequestParam("photo") MultipartFile photo, ModelAndView modelAndView, HttpSession session) {
        User user = (User) session.getAttribute("user");
        try {
            userProfileService.saveImage(photo, user);
        } catch (IOException e) {
            modelAndView.addObject("photoError", "Try again");
        }
        modelAndView.setViewName("changeProfile");
        return modelAndView;
    }

    @PostMapping("/chooseResidenceCountry")
    public String chooseResidenceCountry(Country country, HttpSession session){
        User user = (User) session.getAttribute("user");
        userProfileService.saveResidenceCountry(user, country);
        return "redirect:";
    }

    @PostMapping("/chooseFavoriteCountry")
    public String chooseFavoriteCountry(Country country, HttpSession session){
        User user = (User) session.getAttribute("user");
        userProfileService.saveFavoriteCountry(user, country);
        return "redirect:";
    }

    @PostMapping("/addVisitedCountry")
    public ModelAndView addVisitedCountry(Country country, ModelAndView modelAndView, HttpSession session){
        User user = (User) session.getAttribute("user");
        userProfileService.addVisitedCountry(user, country);
        modelAndView.setViewName("redirect:");
        return modelAndView;
    }

    @PostMapping("/deleteVisitedCountry")
    public ModelAndView deleteVisitedCountry(@RequestParam("countryId") Long countryId, ModelAndView modelAndView, HttpSession session){
        User user = (User) session.getAttribute("user");
        userProfileService.deleteVisitedCountry(user, countryService.getCountryById(countryId));
        modelAndView.setViewName("redirect:");
        return modelAndView;
    }
}
