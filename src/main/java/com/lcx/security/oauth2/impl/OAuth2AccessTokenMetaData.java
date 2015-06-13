package com.lcx.security.oauth2.impl;

/**
 * Created by Liam on 16/05/2015.
 */

public class OAuth2AccessTokenMetaData {

    private long expiresIn;
    private long timestamp;
    private String refreshToken;

    public OAuth2AccessTokenMetaData(long expiresIn, String refreshToken){
        this.expiresIn = expiresIn;
        this.timestamp = System.currentTimeMillis();
        this.refreshToken = refreshToken;
    }

    public boolean isExpired(){
        return System.currentTimeMillis() > (timestamp + (expiresIn*1000));
    }

    public String getRefreshToken(){
        return refreshToken;
    }
}
