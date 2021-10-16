package com.example.travelproject.service;

import com.example.travelproject.entity.Role;
import com.example.travelproject.entity.User;
import com.example.travelproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private UserProfileService userProfileService;

    public User save(User user){
            user.setRole(Role.USER);
            return userRepository.save(user);
    }

    public boolean existsUserByUsername(String username){
        return userRepository.existsUserByUsername(username.toLowerCase());
    }

    public boolean existUserByEmail(String email){
        return  userRepository.existsUserByEmail(email.toLowerCase());
    }

    public Optional<User> findUserByUsernameAndPassword(User user){
        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }

    public User getById(long id){
        return userRepository.getById(id);
    }

    public User changeName(User user, String newName){
        userRepository.changeName(user.getId(), newName);
        User byId = userRepository.getById(user.getId());
        return byId;
    }

    public User changeUsername(User user, String newUsername){
        userRepository.changeUsername(user.getId(), newUsername);
        User byId = userRepository.getById(user.getId());
        return byId;
    }

    public User changeEmail(User user, String newEmail){
        userRepository.changeEmail(user.getId(), newEmail);
        User byId = userRepository.getById(user.getId());
        return byId;
    }

    public User changePassword(User user,String newPassword){
        userRepository.changePassword(user.getId(), newPassword);
        User byId = userRepository.getById(user.getId());
        return byId;
    }
}
