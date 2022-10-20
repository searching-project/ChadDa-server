package com.search.instagramsearching.entity;

import com.search.instagramsearching.dto.request.PostRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sid;
    @Column
    private String description;
    @Column
    private Long sidProfile;
    @Column
    private String postId;
    @Column
    private Long profileId;
    @Column
    private Long locationId;
    @CreatedDate
    private Date cts;
    @Column
    private int postType;
    @Column
    @ColumnDefault("0")
    private Integer numberLikes;

    @Column
    @ColumnDefault("0")
    private Integer numberComments;

    public Posts(PostRequestDto requestDto){
        this.description = requestDto.getDescription();
        this.postType = requestDto.getPostType();
    }
}
