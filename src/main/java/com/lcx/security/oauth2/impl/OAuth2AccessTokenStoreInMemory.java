package com.lcx.security.oauth2.impl;

import com.lcx.security.oauth2.OAuth2AccessTokenStore;
import com.lcx.security.oauth2.exceptions.OAuth2AccessTokenInvalidException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Liam on 23/05/2015.
 */
public class OAuth2AccessTokenStoreInMemory implements OAuth2AccessTokenStore {


    Map<String, OAuth2AccessTokenMetaData> tokens;

    public OAuth2AccessTokenStoreInMemory(){
        tokens = new HashMap<String, OAuth2AccessTokenMetaData>();
    }

    public void addToken(OAuth2AccessToken token, OAuth2AccessTokenMetaData metaData) {
        tokens.put(token.getAcessToken(), metaData);
    }

    public void removeToken(OAuth2AccessToken token) {
        tokens.remove(token.getAcessToken());
    }

    public boolean exists(OAuth2AccessToken token) {
        return tokens.containsKey(token.getAcessToken());
    }

    public boolean isExpired(OAuth2AccessToken token) {
        try {
            return getMetaData(token).isExpired();
        } catch (OAuth2AccessTokenInvalidException e) {
            return false; //if token does not exists, it is not expired
        }
    }

    public OAuth2AccessTokenMetaData getMetaData(OAuth2AccessToken token) throws OAuth2AccessTokenInvalidException {

        if(token == null)
            throw new OAuth2AccessTokenInvalidException("No token provided");

        OAuth2AccessTokenMetaData metaData = tokens.get(token.getAcessToken());

        if(metaData == null)
            throw new OAuth2AccessTokenInvalidException("No metadata found for token");

        return metaData;
    }
}
