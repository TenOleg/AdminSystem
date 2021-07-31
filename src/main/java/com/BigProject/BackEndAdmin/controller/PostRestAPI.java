package com.BigProject.BackEndAdmin.controller;

import com.BigProject.BackEndAdmin.messages.response.ResponseHandler;
import com.BigProject.BackEndAdmin.model.Post;
import com.BigProject.BackEndAdmin.repository.PostStatusCommentRepository;
import com.BigProject.BackEndAdmin.service.impl.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/")
public class PostRestAPI {

    private final PostService postService;
    private final PostStatusCommentRepository postStatusCommentRepository;

    @Autowired
    public PostRestAPI(PostService postService, PostStatusCommentRepository postStatusCommentRepository) {
        this.postService = postService;
        this.postStatusCommentRepository = postStatusCommentRepository;
    }


    @GetMapping("posts")
    public ResponseEntity<Object> getAllPosts(@RequestParam String keyword, @RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "5") int pageSize) {
        try {
            Page<Post> posts = postService.getAll(keyword, pageNo - 1, pageSize);

            if (posts.getTotalElements() == 0) {
                return ResponseHandler.generateResponse(1, HttpStatus.NO_CONTENT, null);
            }

            return ResponseHandler.generateResponse(0, HttpStatus.OK, posts);

        } catch (Exception e) {
            return ResponseHandler.generateResponse(2, HttpStatus.MULTI_STATUS, e);
        }
    }
}
