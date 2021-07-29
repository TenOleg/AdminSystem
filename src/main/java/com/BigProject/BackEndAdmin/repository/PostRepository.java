package com.BigProject.BackEndAdmin.repository;

import com.BigProject.BackEndAdmin.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "select * from post p where CONCAT (p.title) like %?1%", nativeQuery = true)
    Page<Post> findAll(String keyword, Pageable pageable);

}
