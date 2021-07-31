package com.BigProject.BackEndAdmin.repository;

import com.BigProject.BackEndAdmin.model.UserStatusComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStatusCommentRepository extends JpaRepository<UserStatusComment, Long> {
}
