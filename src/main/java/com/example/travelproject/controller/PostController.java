package com.example.travelproject.controller;

import com.example.travelproject.entity.Country;
import com.example.travelproject.entity.Post;
import com.example.travelproject.entity.User;
import com.example.travelproject.service.CountryService;
import com.example.travelproject.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@AllArgsConstructor
@RequestMapping("/newPost")
@SessionAttributes("user")
public class PostController {

    private PostService postService;
    private CountryService countryService;


    @GetMapping()
    public ModelAndView getPostPage(ModelAndView modelAndView){
        modelAndView.addObject("newPost", new Post());
        modelAndView.addObject("countries", countryService.getAll());
        modelAndView.addObject("isImageUpload", 0);
        modelAndView.addObject("isPostUpload", false);
        modelAndView.setViewName("newPost");
        return modelAndView;
    }

    @PostMapping("/uploadImage")
    public ModelAndView uploadPhoto(MultipartFile image, HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        User user = (User) session.getAttribute("user");
        String imageName = postService.saveImage(image, user);
        modelAndView.addObject("imageName", imageName);
        modelAndView.addObject("image", "/user-photos/" + user.getId() + "/" + imageName);
        modelAndView.addObject("newPost", new Post());
        modelAndView.addObject("countries", countryService.getAll());
        modelAndView.addObject("isImageUpload", 1);
        modelAndView.setViewName("newPost");
        return modelAndView;
    }

    @PostMapping("/savePost")
    private ModelAndView savePost(@Valid @ModelAttribute("newPost") Post newPost, BindingResult result, ModelAndView modelAndView, HttpSession session){
        if(result.hasErrors()){
            modelAndView.setViewName("newPost");
            return modelAndView;
        }
        User user = (User) session.getAttribute("user");
        newPost.setUser(user);
        postService.savePost(newPost);
        modelAndView.addObject("isPostUpload", true);
        modelAndView.setViewName("newPost");
        return modelAndView;
    }

    @PostMapping("/addLike")
    private ModelAndView addLike(@RequestParam("postId") Long postId, ModelAndView modelAndView){
        postService.addLike(postService.getPostById(postId));
        modelAndView.setViewName("redirect:/profile");
        return modelAndView;
    }

    @GetMapping("/news")
    private ModelAndView getAllPosts(ModelAndView modelAndView){
        modelAndView.addObject("posts", postService.getAllSortByDate());
        modelAndView.setViewName("news");
        return modelAndView;
    }
}
