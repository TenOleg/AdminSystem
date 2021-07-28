package com.BigProject.BackEndAdmin.repository;

import com.BigProject.BackEndAdmin.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query(value = "select * from user u where CONCAT (u.username, u.first_name, u.last_name, u.email) like %?1%", nativeQuery = true)
    Page<User> findAll(String keyword, Pageable pageable);

}
