package com.lcx.security.oauth2.exceptions;

/**
 * Created by Liam on 16/05/2015.
 */
public class OAuth2Exception extends Exception {

    public OAuth2Exception(String message, String...args){
        super(String.format(message, args));
    }
    public OAuth2Exception(Exception inner){
        super(inner);
    }
}
