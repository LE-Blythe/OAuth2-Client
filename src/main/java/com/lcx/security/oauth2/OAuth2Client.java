package com.lcx.security.oauth2;

import com.lcx.security.oauth2.exceptions.OAuth2AccessTokenExpiredException;
import com.lcx.security.oauth2.exceptions.OAuth2AccessTokenInvalidException;
import com.lcx.security.oauth2.exceptions.OAuth2Exception;
import com.lcx.security.oauth2.impl.OAuth2AccessToken;
import com.lcx.security.oauth2.impl.OAuth2AccessTokenStatus;

import java.net.URI;

/**
 * Created by Liam on 16/05/2015.
 */
public interface OAuth2Client {

    URI authorize() throws OAuth2Exception;
    OAuth2AccessToken obtainToken(String oauthCode) throws OAuth2Exception;

    OAuth2AccessToken refreshToken(OAuth2AccessToken token) throws OAuth2Exception, OAuth2AccessTokenInvalidException;

    OAuth2AccessTokenStatus validateToken(OAuth2AccessToken token);

    OAuth2Response sendAuthorizedRequest(OAuth2AccessToken token, OAuth2Request request) throws OAuth2Exception, OAuth2AccessTokenExpiredException, OAuth2AccessTokenInvalidException;
}
