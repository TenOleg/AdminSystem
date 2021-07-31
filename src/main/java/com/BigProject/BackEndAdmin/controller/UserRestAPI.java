package com.BigProject.BackEndAdmin.controller;

import com.BigProject.BackEndAdmin.exception.UserNotFoundException;
import com.BigProject.BackEndAdmin.messages.response.ResponseHandler;
import com.BigProject.BackEndAdmin.messages.response.UserDto;
import com.BigProject.BackEndAdmin.model.Status;
import com.BigProject.BackEndAdmin.model.User;
import com.BigProject.BackEndAdmin.model.UserStatusComment;
import com.BigProject.BackEndAdmin.repository.UserRepository;
import com.BigProject.BackEndAdmin.repository.UserStatusCommentRepository;
import com.BigProject.BackEndAdmin.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/")
public class UserRestAPI {
    private final UserService userService;
    private final UserRepository userRepository;
    private final UserStatusCommentRepository userStatusCommentRepository;

    @Autowired
    public UserRestAPI(UserService userService, UserRepository userRepository, UserStatusCommentRepository userStatusCommentRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.userStatusCommentRepository = userStatusCommentRepository;
    }

    @GetMapping("users")
    public ResponseEntity<Object> getAllUsers(@RequestParam String keyword, @RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "5") int pageSize) {
        try {
            Page<User> users = userService.getAll(keyword, pageNo - 1, pageSize);

            if (users.getTotalElements() == 0) {
                return ResponseHandler.generateResponse(1, HttpStatus.NO_CONTENT, null);
            }
            List<UserDto> userListDto = new ArrayList<>();
            users.forEach(u -> {
                userListDto.add(UserDto.fromUser(u));
            });

            return ResponseHandler.generateResponse(0, HttpStatus.OK, userListDto);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(2, HttpStatus.MULTI_STATUS, e);
        }
    }

    @GetMapping("user/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable(name = "id") Long id) {
        try {
            User user = userService.findById(id);

            return ResponseHandler.generateResponse(0, HttpStatus.OK, UserDto.fromUser(user));
        } catch (Exception e) {
            return ResponseHandler.generateResponse(2, HttpStatus.MULTI_STATUS, e);
        }
    }

    @PatchMapping("user/{id}")
    public ResponseEntity<Object> changeUserStatus(@PathVariable Long id, @RequestParam Status status) {
        try {
            User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
            user.setStatus(status);
            userService.changedUserStatus(user);

            return ResponseHandler.generateResponse(0, HttpStatus.OK, UserDto.fromUser(user));
        } catch (Exception e) {
            return ResponseHandler.generateResponse(2, HttpStatus.MULTI_STATUS, e);
        }
    }

    @PostMapping("user")
    public ResponseEntity<Object> createStatusComment(@RequestBody UserStatusComment userStatusComment) {
        try {
            UserStatusComment statusComment = userStatusCommentRepository.save(userStatusComment);
            return ResponseHandler.generateResponse(0, HttpStatus.CREATED, statusComment);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(2, HttpStatus.MULTI_STATUS, e);
        }
    }
}
