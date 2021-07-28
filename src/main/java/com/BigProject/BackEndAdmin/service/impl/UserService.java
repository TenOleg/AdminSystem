package com.BigProject.BackEndAdmin.service.impl;

import com.BigProject.BackEndAdmin.model.User;
import org.springframework.data.domain.Page;

public interface UserService {

    Page<User> getAll(String keyword, int pageNo, int pageSize);

    User findByUsername(String username);

    User findById(Long id);

    void changedUserStatus(User user);

}
