package com.search.instagramsearching;

public class CacheKey {
    private CacheKey() {
    }
    public static final int DEFAULT_EXPIRE_SEC = 60;
    public static final String REFRESH = "refreshToken";
    public static final String USER = "user";
    public static final int REFRESH_TOKEN_VALID_TIME = 24 * 60 * 60;
}