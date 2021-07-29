package com.BigProject.BackEndAdmin.service.impl;

import com.BigProject.BackEndAdmin.model.Post;
import org.springframework.data.domain.Page;

public interface PostService {

    Page<Post> getAll(String keyword, int pageNo, int pageSize);
}
