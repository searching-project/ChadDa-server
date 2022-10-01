package com.search.instagramsearching.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Locations {
    @Id
    private Long sid;
    @Column
    private String name;
    @Column
    private String street;
    @Column
    private String zip;
    @Column
    private String city;
    @Column
    private String region;
    @Column
    private String cd;
    @Column
    private String phone;
    @Column
    private boolean ajExactCityMatch;
    @Column
    private boolean ajExactCountryMatch;
    @Column
    private String blurb;
    @Column
    private String dirCityId;
    @Column
    private String dirCityName;
    @Column
    private String dirCitySlug;
    @Column
    private String dirCountryId;
    @Column
    private String dirCountryName;
    @Column
    private String lat;
    @Column
    private String lng;
    @Column
    private String privaryAliasOnFb;
    @Column
    private String slug;
    @Column
    private String website;
    @Column
    private Date cts;
}
