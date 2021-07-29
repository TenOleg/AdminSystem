package com.BigProject.BackEndAdmin.messages.response;

import com.BigProject.BackEndAdmin.model.Status;
import com.BigProject.BackEndAdmin.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private String email;
    private Status status;
    private Date created;
    private Date updated;


    public User toUser() {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAge(age);
        user.setGender(gender);
        user.setEmail(email);
        user.setStatus(Status.valueOf(status.name()));
        user.setCreated(created);
        user.setUpdated(updated);
        return user;
    }

    public static UserDto fromUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setAge(user.getAge());
        userDto.setGender(user.getGender());
        userDto.setEmail(user.getEmail());
        userDto.setStatus(Status.valueOf(user.getStatus().name()));
        userDto.setCreated(user.getCreated());
        userDto.setUpdated(user.getUpdated());
        return userDto;
    }
}