package com.lcx.security.oauth2.exceptions;

/**
 * Created by Liam on 17/05/2015.
 */
public class OAuth2AccessTokenInvalidException extends Exception {

    public OAuth2AccessTokenInvalidException(){
        super();
    }
    public OAuth2AccessTokenInvalidException(String msg, String...args){
        super(String.format(msg, args));
    }
}
