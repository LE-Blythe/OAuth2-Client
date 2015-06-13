package com.lcx.security.oauth2.impl;

/**
 * Created by Liam on 19/05/2015.
 */
public class OAuth2RequestSimple extends OAuth2RequestAbstract {

    public OAuth2RequestSimple(String url){
        super(url, OAuth2HttpMethod.GET);
    }

    public boolean hasContent() {
        return false;
    }
    public String getContent() {
        return null;
    }
}
