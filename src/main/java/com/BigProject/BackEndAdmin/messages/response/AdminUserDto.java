package com.BigProject.BackEndAdmin.messages.response;

import com.BigProject.BackEndAdmin.model.Status;
import com.BigProject.BackEndAdmin.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminUserDto {
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

    public static AdminUserDto fromUser(User user) {
        AdminUserDto adminUserDto = new AdminUserDto();
        adminUserDto.setId(user.getId());
        adminUserDto.setUsername(user.getUsername());
        adminUserDto.setFirstName(user.getFirstName());
        adminUserDto.setLastName(user.getLastName());
        adminUserDto.setAge(user.getAge());
        adminUserDto.setGender(user.getGender());
        adminUserDto.setEmail(user.getEmail());
        adminUserDto.setStatus(Status.valueOf(user.getStatus().name()));
        adminUserDto.setCreated(user.getCreated());
        adminUserDto.setUpdated(user.getUpdated());
        return adminUserDto;
    }
}