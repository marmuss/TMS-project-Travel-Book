package com.example.travelproject.repository;

import com.example.travelproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsUserByUsername(String username);
    boolean existsUserByEmail(String email);

    Optional<User> findByUsernameAndPassword(String username, String password);

    @Transactional
    @Modifying
    @Query("update User u set u.name = :name WHERE u.id = :id")
    void changeName(@Param("id") Long id, @Param("name") String name);

    @Transactional
    @Modifying
    @Query("update User u set u.username = :username WHERE u.id = :id")
    void changeUsername(@Param("id") Long id, @Param("username") String username);

    @Transactional
    @Modifying
    @Query("update User u set u.email = :email WHERE u.id = :id")
    void changeEmail(@Param("id") Long id, @Param("email") String email);

    @Transactional
    @Modifying
    @Query("update User u set u.password = :password WHERE u.id = :id")
    void changePassword(@Param("id") Long id, @Param("password") String password);
}
