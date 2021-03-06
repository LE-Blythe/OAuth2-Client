package com.lcx.security.oauth2.impl;

import com.lcx.security.oauth2.OAuth2Response;
import com.lcx.security.oauth2.OAuth2ResponseFactory;
import com.lcx.security.oauth2.exceptions.OAuth2Exception;

/**
 * Created by Liam on 19/05/2015.
 */
public class OAuth2ResponseFactoryJson implements OAuth2ResponseFactory {

    public OAuth2Response createResponse(int httpResponseCode, String content, String contentType) throws OAuth2Exception {

        if(!contentType.toLowerCase().contains("json"))
            throw new OAuth2Exception("Expected Json content-type. Received: %s", content);

        return new OAuth2ResponseJson(httpResponseCode, content);
    }
}
