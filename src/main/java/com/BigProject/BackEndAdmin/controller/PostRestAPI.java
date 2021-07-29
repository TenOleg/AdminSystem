package com.BigProject.BackEndAdmin.controller;

import com.BigProject.BackEndAdmin.messages.response.UserDto;
import com.BigProject.BackEndAdmin.model.Post;
import com.BigProject.BackEndAdmin.service.impl.PostService;
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
public class PostRestAPI {

    private final PostService postService;

    @Autowired
    public PostRestAPI(PostService postService) {
        this.postService = postService;
    }


    @GetMapping("posts")
    public ResponseEntity<Map<Object, Object>> getAllPosts(@RequestParam String keyword, @RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "5") int pageSize) {
        try {
            Map<Object, Object> response = new HashMap<>();
            Page<Post> posts = postService.getAll(keyword, pageNo - 1, pageSize);

            if (posts.getTotalElements() == 0) {
                response.put("message", "No any posts found");
                response.put("resultCode", 1);
                return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
            }

            response.put("data", posts);
            response.put("resultCode", 0);
            return new ResponseEntity<>(response, HttpStatus.OK);


        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
