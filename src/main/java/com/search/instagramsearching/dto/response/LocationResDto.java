package com.search.instagramsearching.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LocationResDto {
    private String name;
    private String street;
    private String city;
    private String region;
}
