package com.BigProject.BackEndAdmin.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "post")
@Data
@EqualsAndHashCode(callSuper = false)
public class Post extends BaseEntity{

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "city_id")
    private Long cityId;

    @Column(name = "description")
    private String description;

    @Column(name = "district_id")
    private Long districtId;

    @Column(name = "title")
    private String title;

    @Column(name = "type")
    private String type;
}
