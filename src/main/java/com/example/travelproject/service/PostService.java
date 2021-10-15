package com.example.travelproject.service;

import com.example.travelproject.entity.Comment;
import com.example.travelproject.entity.Post;
import com.example.travelproject.entity.User;
import com.example.travelproject.repository.CommentRepository;
import com.example.travelproject.repository.PostRepository;
import com.example.travelproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class PostService {

    private UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private ImageService imageService;
    private UserProfileService userProfileService;

    public String saveImage(MultipartFile image, User user) throws IOException {
        String fileName = StringUtils.cleanPath(image.getOriginalFilename());
        String uploadDir = "user-photos/" + user.getId();
        imageService.uploadImage(uploadDir, fileName, image);
        return image.getOriginalFilename();
    }

    public void savePost(Post post){
        post.setDateTime(LocalDateTime.now());
        postRepository.save(post);
    }

    public void delete(Post post){
        postRepository.delete(post);
    }

    public Post getPostById(long id){
        return postRepository.getById(id);
    }

    public List<Post> getAll(){
        return postRepository.findAll();
    }

    public List<Post> getAllSortByDate(){
        List<Post> posts = postRepository.findAll(Sort.by(Sort.Direction.DESC, "dateTime"));
        return posts;
    }

    public List<Post> getAllByUser(User user){
        return postRepository.findAllByUser(user);
    }

    public List<Post> getAllByUserId(long id){
        return postRepository.findAllByUser(userRepository.getById(id));
    }

    public void addComment(User user, String commentText, Long postId){
        Post post = getPostById(postId);
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setText(commentText);
        comment.setPost(post);
        comment.setDateTime(LocalDateTime.now());
        commentRepository.save(comment);
    }

    public Optional<List<Comment>> getAllCommentsByPost(Post post){
        return commentRepository.findAllByPost(post);
    }

    public void addLike(Post post){
        int likes = post.getLikes() + 1;
        post.setLikes(likes);
        postRepository.save(post);
    }
}
