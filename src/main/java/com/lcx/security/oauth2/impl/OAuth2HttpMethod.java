package com.lcx.security.oauth2.impl;

import org.apache.oltu.oauth2.common.OAuth;

/**
 * Created by Liam on 19/05/2015.
 */
public enum OAuth2HttpMethod {

    GET(OAuth.HttpMethod.GET),
    POST(OAuth.HttpMethod.POST);

    String method;

    OAuth2HttpMethod(String method) {
        this.method = method;
    }


    @Override
    public String toString(){
        return method;
    }
}
