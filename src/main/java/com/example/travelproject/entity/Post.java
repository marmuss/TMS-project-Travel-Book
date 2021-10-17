package com.example.travelproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "POSTS")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private User user;
    @NotEmpty(message = "Field must not be empty")
    @NotBlank(message = "Field must not be blank")
    private String title;
    @OneToOne
    private Country country;
    @NotEmpty(message = "Field must not be empty")
    @NotBlank(message = "Field must not be blank")
    private String place;
    @NotEmpty(message = "Field must not be empty")
    @NotBlank(message = "Field must not be blank")
    @Column(length=1000)
    private String text;
    private String image;
    private LocalDateTime dateTime;
    @OneToMany (mappedBy="post", fetch=FetchType.EAGER)
    private List<Comment> comments;
    private int likes;

    @Transient
    public String getImagePath(){
        if (image == null || id == 0) return null;
        return "/user-photos/" + user.getId() + "/" + image;
    }

}
