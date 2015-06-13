package com.lcx.security.oauth2;

import com.lcx.security.oauth2.exceptions.OAuth2Exception;

/**
 * Created by Liam on 19/05/2015.
 */
public interface OAuth2ResponseFactory {

    OAuth2Response createResponse(int httpResponseCode, String content, String contentType) throws OAuth2Exception;
}
