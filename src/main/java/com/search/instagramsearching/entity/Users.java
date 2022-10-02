package com.search.instagramsearching.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
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
    private int following;

    @Column
    private int followers;

    @Column
    private int nPosts;

    @Column
    private String url;

    @Column
    private String cts;

    @Column
    private String isBusinessAccount;
}
