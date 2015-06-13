package com.lcx.security.oauth2.impl;

import com.google.gson.Gson;

/**
 * Created by Liam on 19/05/2015.
 */
public class OAuth2RequestJson<T> extends OAuth2RequestAbstract {

    private T object;

    public OAuth2RequestJson(String url, OAuth2HttpMethod method, T object){
        super(url, method, "application/json");

        this.object = object;
    }

    public boolean hasContent() {
        return true;
    }

    public String getContent() {
        return new Gson().toJson(object);
    }
}
