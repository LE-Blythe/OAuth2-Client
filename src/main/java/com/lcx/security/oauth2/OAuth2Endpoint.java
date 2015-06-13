package com.lcx.security.oauth2;

/**
 * Created by Liam on 19/05/2015.
 */
public interface OAuth2Endpoint {

    String getAuthorizationUrl();
    String getTokenUrl();
    String getRedirectUrl();
    String getClientId();
    String getClientSecret();
    String getScopes();
}
