package com.search.instagramsearching.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationResDto {
    private int sid;
    private String name;
    private String street;
    private String city;
    private String region;
}
