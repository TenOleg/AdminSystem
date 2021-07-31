package com.BigProject.BackEndAdmin.repository;

import com.BigProject.BackEndAdmin.model.PostStatusComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostStatusCommentRepository extends JpaRepository<PostStatusComment, Long> {
}
