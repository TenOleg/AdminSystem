package com.BigProject.BackEndAdmin.service;

import com.BigProject.BackEndAdmin.model.Post;
import com.BigProject.BackEndAdmin.repository.PostRepository;
import com.BigProject.BackEndAdmin.service.impl.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


    @Override
    public Page<Post> getAll(String keyword, int pageNo, int pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Post> result = postRepository.findAll(keyword, paging);
        log.info("IN getAll - {} posts found", result.getTotalElements());
        return result;
    }
}
