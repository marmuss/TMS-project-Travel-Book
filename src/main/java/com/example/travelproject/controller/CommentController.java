package com.example.travelproject.controller;

import com.example.travelproject.entity.Post;
import com.example.travelproject.entity.User;
import com.example.travelproject.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@AllArgsConstructor
@SessionAttributes("user")
@RequestMapping("/discussion")
public class CommentController {
    private PostService postService;

    @GetMapping("/{postId}")
    public ModelAndView getPostById(@PathVariable("postId") long postId, ModelAndView modelAndView){
        Post post = postService.getPostById(postId);
        modelAndView.addObject("post", post);
        modelAndView.addObject("comments", postService.getAllCommentsByPost(post));
        modelAndView.setViewName("discussion");
        return modelAndView;
    }

    @PostMapping("/addComment")
    public ModelAndView addComment(@RequestParam("comment") String comment, @RequestParam("postId") Long postId,
                                   ModelAndView modelAndView, HttpSession session){
        User user = (User) session.getAttribute("user");
        postService.addComment(user, comment, postId);
        modelAndView.setViewName("redirect:/profile");
        return modelAndView;
    }
}
