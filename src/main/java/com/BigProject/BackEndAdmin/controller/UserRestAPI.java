package com.BigProject.BackEndAdmin.controller;

import com.BigProject.BackEndAdmin.exception.UserNotFoundException;
import com.BigProject.BackEndAdmin.messages.response.UserDto;
import com.BigProject.BackEndAdmin.model.Status;
import com.BigProject.BackEndAdmin.model.User;
import com.BigProject.BackEndAdmin.repository.UserRepository;
import com.BigProject.BackEndAdmin.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/")
public class UserRestAPI {
    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public UserRestAPI(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("users")
    public ResponseEntity<Map<Object, Object>> getAllUsers(@RequestParam String keyword, @RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "5") int pageSize) {
        try {
            Map<Object, Object> response = new HashMap<>();
            Page<User> users = userService.getAll(keyword, pageNo - 1, pageSize);

            if (users.getTotalElements() == 0) {
                response.put("message", "No any users found");
                response.put("resultCode", 1);
                return ResponseEntity.ok(response);
            }

            List<UserDto> userListDto = new ArrayList<>();
            users.forEach(u -> {
                userListDto.add(UserDto.fromUser(u));
            });

            response.put("data", userListDto);
            response.put("totalCount", users.getTotalElements());
            response.put("resultCode", 0);
            return new ResponseEntity<>(response, HttpStatus.OK);


        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("user/{id}")
    public ResponseEntity<Map<Object, Object>> getUserById(@PathVariable(name = "id") Long id) {
        Map<Object, Object> response = new HashMap<>();
        User user = userService.findById(id);

        if (user != null) {
            UserDto result = UserDto.fromUser(user);

            response.put("data", result);
            response.put("resultCode", 0);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        response.put("resultCode", 1);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @PatchMapping("user/{id}")
    public ResponseEntity<Map<Object, Object>> changeUserStatus(@PathVariable Long id, @RequestParam Status status) {
        Map<Object, Object> response = new HashMap<>();
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        user.setStatus(status);
        userService.changedUserStatus(user);

        response.put("data", UserDto.fromUser(user));
        response.put("resultCode", 0);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
