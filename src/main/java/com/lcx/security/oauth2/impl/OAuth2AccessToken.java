package com.lcx.security.oauth2.impl;

/**
 * Created by Liam on 16/05/2015.
 */

public class OAuth2AccessToken {

    private static final String TOKEN_TYPE = "Bearer";
    private String accessToken;

    public OAuth2AccessToken(String accessToken){
        this.accessToken = accessToken;
    }

    public String getAcessToken(){
        return accessToken;
    }

    public String getTokenType(){
        return TOKEN_TYPE;
    }


    private static String getTokenFromAuthorizationString(String s){

        String[] parts = s.split(" ");

        if(parts.length < 2)
            return null;

        return parts[1];
    }

    public static OAuth2AccessToken fromString(String authorizationString){
        return new OAuth2AccessToken(getTokenFromAuthorizationString(authorizationString));
    }

    public static OAuth2AccessToken valueOf(String authorizationString){
        return new OAuth2AccessToken(getTokenFromAuthorizationString(authorizationString));
    }
}
