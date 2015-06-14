package com.lcx.security.oauth2.impl;

import com.google.gson.Gson;
import com.lcx.security.oauth2.OAuth2Response;

/**
 * Created by Liam on 19/05/2015.
 */
public class OAuth2ResponseJson implements OAuth2Response {

    private final int responseCode;
    private final String content;
    private final Gson gson;

    public OAuth2ResponseJson(int responseCode, String content, Gson gson){
        this.responseCode = responseCode;
        this.content = content;
        this.gson = gson;
    }

    public <T> T getEntity(Class<T> classOfT) {
        return gson.fromJson(content, classOfT);
    }

    public int getHttpResponseCode() {
        return responseCode;
    }
}
