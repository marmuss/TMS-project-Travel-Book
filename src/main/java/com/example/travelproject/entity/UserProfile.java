package com.example.travelproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PROFILE")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    private User user;
    @OneToOne
    private Country residenceCountry;
    private String photo;
    @OneToMany
    private Set<Country> visitedCountries;
    @OneToOne
    private Country favoriteCountry;
    private LocalDateTime creationDate;
    @ManyToMany
    private Set<UserProfile> companions;

    @Transient
    public String getPhotoPath() {
        if (id == 0) {
            return null;
        } else {
            if (photo == null){
                photo = "default.png";
            }
            return "/user-photos/" + user.getId() + "/" + photo;
        }
    }
}
