package com.lcx.security.oauth2.exceptions;

/**
 * Created by Liam on 17/05/2015.
 */
public class OAuth2HeaderAlreadySetException extends Exception {

    public OAuth2HeaderAlreadySetException(String headerName){
        super(String.format("Header %s already set by implementation", headerName));
    }
}
