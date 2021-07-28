package com.BigProject.BackEndAdmin.service;

import com.BigProject.BackEndAdmin.model.User;
import com.BigProject.BackEndAdmin.repository.UserRepository;
import com.BigProject.BackEndAdmin.service.impl.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Page<User> getAll(String keyword, int pageNo, int pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<User> result = userRepository.findAll(keyword, paging);
        log.info("IN getAll - {} users found", result.getTotalElements());
        return result;
    }

    @Override
    public User findByUsername(String username) {
        return null;
    }

    @Override
    public User findById(Long id) {
        User result = userRepository.findById(id).orElse(null);

        if (result == null) {
            log.warn("IN findById - no user found by id: {}", id);
            return null;
        }

        log.info("IN findById - user: {} found by id: {}", result);
        return result;
    }

    @Override
    public void changedUserStatus(User user) {
        userRepository.save(user);
        log.info("IN changeUserStatus");
    }
}
