package com.lcx.security.oauth2;

import com.lcx.security.oauth2.exceptions.OAuth2AccessTokenInvalidException;
import com.lcx.security.oauth2.impl.OAuth2AccessToken;
import com.lcx.security.oauth2.impl.OAuth2AccessTokenMetaData;

/**
 * Created by Liam on 16/05/2015.
 */
public interface OAuth2AccessTokenStore {

    void addToken(OAuth2AccessToken token, OAuth2AccessTokenMetaData metaData);
    void removeToken(OAuth2AccessToken token);

    OAuth2AccessTokenMetaData getMetaData(OAuth2AccessToken token) throws OAuth2AccessTokenInvalidException;

    boolean exists(OAuth2AccessToken token);
    boolean isExpired(OAuth2AccessToken token);

}
