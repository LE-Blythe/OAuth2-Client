package com.lcx.security.oauth2.impl;

import com.google.gson.Gson;
import com.lcx.security.oauth2.OAuth2Response;

/**
 * Created by Liam on 19/05/2015.
 */
public class OAuth2ResponseJson implements OAuth2Response {

    private final int responseCode;
    private final String content;

    public OAuth2ResponseJson(int responseCode, String content){
        this.responseCode = responseCode;
        this.content = content;
    }

    public <T> T getContentAs(Class<T> classOfT) {
        return new Gson().fromJson(content, classOfT);
    }

    public String getContent() {
        return content;
    }

    public int getHttpResponseCode() {
        return responseCode;
    }
}
