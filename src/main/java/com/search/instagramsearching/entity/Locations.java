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
    private int sid;
    @Column
    private int id;
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
    private String ajExactCityMatch;
    @Column
    private String ajExactCountryMatch;
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
    private double lat;
    @Column
    private double lng;
    @Column
    private String privaryAliasOnFb;
    @Column
    private String slug;
    @Column
    private String website;
    @Column
    private Date cts;
}
