package com.search.instagramsearching.entity;

import com.search.instagramsearching.dto.request.PostRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class PostsTest {
    @Test
    @DisplayName("정상 케이스")
    void createPosts_Normal(){
        String description = "feji";
        Long sidProfile;
        String postId;
        Long profileId;
        Long locationId;
        int postType = 1;
        PostRequestDto requestDto = PostRequestDto.builder()
                .description(description)
                .postType(postType)
                .build();

        Posts posts = new Posts(requestDto);

        assertEquals(posts.getDescription(),description);
        assertNull(posts.getNumberComments());
        assertNull(posts.getSid());
        assertNotNull(posts.getCts());
    }
}