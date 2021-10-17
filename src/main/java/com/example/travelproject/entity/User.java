package com.example.travelproject.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "Field must not be empty")
    @NotBlank(message = "Field must not be blank")
    @Size(min = 3, message = "The Name should have at least 3 characters")
    private String name;

    @NotBlank(message = "Field must not be empty")
    @Size(min = 3, max = 13, message = "The Username should have at least 3 characters, but no more than 13")
    private String username;

    @NotEmpty(message = "Field must not be empty")
    @NotBlank(message = "Field must not be blank")
    @Email
    private String email;

    @NotBlank(message = "Field must not be blank")
    @Size(min = 8, message = "The Password should have at least 8 characters")
    private String password;
    private Role role;
}


