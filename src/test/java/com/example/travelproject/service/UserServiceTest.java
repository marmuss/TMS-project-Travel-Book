package com.example.travelproject.service;

import com.example.travelproject.entity.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    void existsUserByUsername() {
        User user = new User(0, "test", "test", "test@test.com", "12345678", null);
        boolean test = userService.existsUserByUsername("test");
        Assert.assertTrue(test);
    }

    @Test
    void getById() {
        User byId = userService.getById(1);
        Assert.assertTrue(byId.getId() == 1);
        Assert.assertNotNull(byId);
    }

    @Test
    void save() {
        User user = new User(0, "test", "test", "test@test.com", "12345678", null);
        User savedUser = userService.save(user);
        Assert.assertNotNull(savedUser.getRole());
    }

    @Test
    void changeName() {
        User byId = userService.getById(2);
        userService.changeName(byId, "test3");
        User changedUser = userService.getById(2);
        String userName = changedUser.getName();

        Assert.assertTrue(userName.equals("test3"));
    }

    @Test
    void changeUsername() {
        User byId = userService.getById(2);
        userService.changeUsername(byId, "test3");
        User changedUser = userService.getById(2);
        String username = changedUser.getUsername();

        Assert.assertTrue(username.equals("test3"));
    }

    @Test
    void changeEmail() {
        User byId = userService.getById(2);
        userService.changeEmail(byId, "test3@test.com");
        User changedUser = userService.getById(2);
        String email = changedUser.getEmail();

        Assert.assertTrue(email.equals("test3@test.com"));
    }

    @Test
    void changePassword() {
        User byId = userService.getById(2);
        userService.changePassword(byId, "87654321");
        User changedUser = userService.getById(2);
        String password = changedUser.getPassword();

        Assert.assertTrue(password.equals("87654321"));
    }
}