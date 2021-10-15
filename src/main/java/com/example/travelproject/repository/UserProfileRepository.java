package com.example.travelproject.repository;

import com.example.travelproject.entity.Country;
import com.example.travelproject.entity.User;
import com.example.travelproject.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByUser(User user);

    @Transactional
    @Modifying
    @Query("update UserProfile p set p.photo = :photo WHERE p.id = :id")
    void setPhoto(@Param("id") Long id, @Param("photo") String photo);
}
