package com.BigProject.BackEndAdmin.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user_status_comment")
@Data
@EqualsAndHashCode(callSuper = false)
public class UserStatusComment extends BaseEntity{

    @Column(name = "comment")
    private String comment;

    @Column(name = "user_id")
    private Long userId;

}
