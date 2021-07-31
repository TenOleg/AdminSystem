package com.BigProject.BackEndAdmin.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "post_status_comment")
@Data
@EqualsAndHashCode(callSuper = false)
public class PostStatusComment extends BaseEntity{

    @Column(name = "comment")
    private String comment;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "post_id")
    private Long postId;

}
