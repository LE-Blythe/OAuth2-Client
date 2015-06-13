package com.lcx.security.oauth2.impl;

import com.lcx.security.oauth2.OAuth2Request;
import com.lcx.security.oauth2.exceptions.OAuth2HeaderAlreadySetException;
import org.apache.oltu.oauth2.common.OAuth;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Liam on 19/05/2015.
 */
public abstract class OAuth2RequestAbstract implements OAuth2Request {

    protected final String CONTENT_TYPE_HEADER = OAuth.HeaderType.CONTENT_TYPE;

    protected Map<String, String> headers;

    private String url;
    private OAuth2HttpMethod httpMethod;

    public OAuth2RequestAbstract(String url, OAuth2HttpMethod httpMethod, String contentTypeValue){
        this(url, httpMethod);
        this.headers.put(CONTENT_TYPE_HEADER, contentTypeValue);
    }

    public OAuth2RequestAbstract(String url, OAuth2HttpMethod httpMethod){

        this.headers = new HashMap<String, String>();
        this.url = url;
        this.httpMethod = httpMethod;
    }

    public void addHeader(String name, String value) throws OAuth2HeaderAlreadySetException {

        if(headers.containsKey(name))
            throw new OAuth2HeaderAlreadySetException(name);

        this.headers.put(name, value);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getUrl() {
        return url;
    }

    public OAuth2HttpMethod getHttpMethod() {
        return httpMethod;
    }
}
