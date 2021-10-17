package com.example.travelproject.controller;

import com.example.travelproject.entity.Country;
import com.example.travelproject.entity.User;
import com.example.travelproject.service.CountryService;
import com.example.travelproject.service.UserProfileService;
import com.example.travelproject.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@AllArgsConstructor
@RequestMapping("/changeProfile")
public class ChangeProfileController {

    private UserService userService;
    private UserProfileService userProfileService;
    private CountryService countryService;

    private final Logger log = LoggerFactory.getLogger(ChangeProfileController.class);


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
        log.info("Name change process started");
        User user =(User) session.getAttribute("user");
        User changedUser = userService.changeName(user, newName);
        session.setAttribute("user", changedUser);
        log.info("User changed name");
        modelAndView.setViewName("changeProfile");
        return modelAndView;
    }

    @PostMapping("/changeUsername")
    public ModelAndView  changeUsername(@RequestParam("newUsername") String newUsername, HttpSession session){
        log.info("Username change process started");
        ModelAndView modelAndView = new ModelAndView();
        User user =(User) session.getAttribute("user");
        if(userService.existsUserByUsername(newUsername)){
            modelAndView.addObject("usernameIsExist", true);
            log.error("User with this username already exist");
        } else {
            User changedUser = userService.changeUsername(user, newUsername);
            session.setAttribute("user", changedUser);
            log.info("User changed username");
        }
        modelAndView.setViewName("changeProfile");
        return modelAndView;
    }

    @PostMapping("/changeEmail")
    public ModelAndView changeEmail(@RequestParam("newEmail") String newEmail, @RequestParam("confirmEmail") String confirmEmail,
                                    HttpSession session){
        log.info("Email change process started");
        ModelAndView modelAndView = new ModelAndView();
        User user =(User) session.getAttribute("user");
        if(newEmail.equals(confirmEmail)){
            if (userService.existUserByEmail(newEmail)){
                modelAndView.addObject("emailIsExist", true);
                log.error("User with this email already exist");
            } else {
                User changedUser = userService.changeEmail(user, newEmail);
                session.setAttribute("user", changedUser);
                log.info("User changed email");
            }
        } else {
            modelAndView.addObject("emailsNotMatch", true);
            log.error("Email does not match confirmation");
        }
        modelAndView.setViewName("changeProfile");
        return modelAndView;
    }

    @PostMapping("/changePassword")
    public ModelAndView changePassword(@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword,
                                       @RequestParam("confirmNewPassword") String confirmNewPassword, HttpSession session){
        log.info("Password change process started");
        ModelAndView modelAndView = new ModelAndView();
        User user =(User) session.getAttribute("user");
        if (user.getPassword().equals(oldPassword)){
            if(newPassword.equals(confirmNewPassword)){
                User changedUser = userService.changePassword(user, newPassword);
                session.setAttribute("user", changedUser);
                log.info("User changed password");
            } else {
                modelAndView.addObject("passwordsNotMatch", true);
                log.error("Password does not match confirmation");
            }
        } else {
            modelAndView.addObject("wrongPassword", true);
            log.error("Wrong passworg");
        }
        modelAndView.setViewName("changeProfile");
        return modelAndView;
    }

    @PostMapping("/uploadPhoto")
    public ModelAndView uploadPhoto (@RequestParam("photo") MultipartFile photo, ModelAndView modelAndView, HttpSession session) {
        log.info("Photo upload process started");
        User user = (User) session.getAttribute("user");
        try {
            userProfileService.saveImage(photo, user);
            log.error("Photo upload completed");
        } catch (IOException e) {
            modelAndView.addObject("photoError", "Try again");
            log.error("Photo upload failed");
        }
        modelAndView.setViewName("changeProfile");
        return modelAndView;
    }

    @PostMapping("/chooseResidenceCountry")
    public String chooseResidenceCountry(Country country, HttpSession session){
        User user = (User) session.getAttribute("user");
        userProfileService.saveResidenceCountry(user, country);
        log.info("Residence country selected");
        return "redirect:";
    }

    @PostMapping("/chooseFavoriteCountry")
    public String chooseFavoriteCountry(Country country, HttpSession session){
        User user = (User) session.getAttribute("user");
        userProfileService.saveFavoriteCountry(user, country);
        log.info("Favorite country selected");
        return "redirect:";
    }

    @PostMapping("/addVisitedCountry")
    public ModelAndView addVisitedCountry(Country country, ModelAndView modelAndView, HttpSession session){
        User user = (User) session.getAttribute("user");
        userProfileService.addVisitedCountry(user, country);
        modelAndView.setViewName("redirect:");
        log.info("Visited country selected");
        return modelAndView;
    }

    @PostMapping("/deleteVisitedCountry")
    public ModelAndView deleteVisitedCountry(@RequestParam("countryId") Long countryId, ModelAndView modelAndView, HttpSession session){
        User user = (User) session.getAttribute("user");
        userProfileService.deleteVisitedCountry(user, countryService.getCountryById(countryId));
        modelAndView.setViewName("redirect:");
        log.info("Visited country deleted");
        return modelAndView;
    }
}
