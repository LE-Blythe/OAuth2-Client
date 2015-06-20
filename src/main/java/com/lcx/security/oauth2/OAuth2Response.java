package com.lcx.security.oauth2;

/**
 * Created by Liam on 19/05/2015.
 */
public interface OAuth2Response {

    <T> T getContentAs(Class<T> classOfT);
    String getContent();
    int getHttpResponseCode();
}
