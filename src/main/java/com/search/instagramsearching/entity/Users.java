package com.search.instagramsearching.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Users {
    @Id
    private Long sid;

    @Column
    private Long profileId;

    @Column
    private String profileName;

    @Column
    private String firstnameLastname;

    @Column
    private String description;

    @Column
    private Integer following;

    @Column
    private Integer followers;

    @Column
    private Integer nPosts;

    @Column
    private String url;

    @Column
    private LocalDateTime cts;

    @Column
    private Boolean businessAccountTf;
}
