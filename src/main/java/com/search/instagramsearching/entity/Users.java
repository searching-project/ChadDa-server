package com.search.instagramsearching.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sid;

    @Column
    private Long profileId;
    @Column
    private String password;

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

    public Users(String username, String password, String firstnameLastname) {
        this.profileName = username;
        this.password = password;
        this.firstnameLastname = firstnameLastname;
    }
    public boolean validatePassword(PasswordEncoder passwordEncoder, String password) {
        return passwordEncoder.matches(password, this.password);
    }
    
//    public void update(String password)
//        this.password = password;
//    }

}
