package com.example.travelproject.controller;

import com.example.travelproject.entity.Country;
import com.example.travelproject.entity.Role;
import com.example.travelproject.entity.User;
import com.example.travelproject.service.CountryService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
@SessionAttributes("user")
public class AdminController {

    private CountryService countryService;
    private static Logger log = LoggerFactory.getLogger(AdminController.class);

    @GetMapping()
    public ModelAndView getAdminPage(ModelAndView modelAndView, HttpSession session){
        if(session.getAttribute("user") == null){
            modelAndView.setViewName("redirect:/home");
            log.error("User is not authorized");
            return modelAndView;
        } else {
            User user = (User) session.getAttribute("user");
            if (!user.getRole().equals(Role.ADMIN)){
                modelAndView.setViewName("redirect:/home");
                log.error("User is not admin");
                return modelAndView;
            }
        }
        modelAndView.addObject("countries", countryService.getAllSortedByContinent());
        modelAndView.addObject("newCountry", new Country());
        modelAndView.setViewName("admin");
        return modelAndView;
    }

    @PostMapping("/addCountry")
    public ModelAndView addCountry(@Valid @ModelAttribute("newCountry") Country country, BindingResult result, ModelAndView modelAndView){
        if(result.hasErrors()){
            modelAndView.setViewName("admin");
            log.error("Validation error");
            return modelAndView;
        }
        countryService.addCountry(country);
        log.info("Country added");
        modelAndView.setViewName("redirect:");
        return modelAndView;
    }

    @PostMapping("/delete")
    private String deleteCountry(@RequestParam Long countryId){
        countryService.deleteById(countryId);
        log.info("Country deleted");
        return "redirect:/admin";
    }
}
