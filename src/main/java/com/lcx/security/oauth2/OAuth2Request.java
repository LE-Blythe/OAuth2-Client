package com.lcx.security.oauth2;

import com.lcx.security.oauth2.exceptions.OAuth2HeaderAlreadySetException;
import com.lcx.security.oauth2.impl.OAuth2HttpMethod;

import java.util.Map;

/**
 * Created by Liam on 19/05/2015.
 */
public interface OAuth2Request {

    /**
     * Get Request Headers, including content-type
     * @return
     */
    Map<String, String> getHeaders();
    void addHeader(String name, String value) throws OAuth2HeaderAlreadySetException;

    boolean hasContent();
    String getContent();
    String getUrl();

    OAuth2HttpMethod getHttpMethod();
}
