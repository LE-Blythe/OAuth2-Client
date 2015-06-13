package com.lcx.security.oauth2.impl;

import lombok.Getter;

/**
 * Created by Liam on 25/05/2015.
 */
@Getter
public class OAuth2AccessTokenStatus {

    private boolean known;
    private boolean expired;

    public OAuth2AccessTokenStatus(boolean known, boolean expired){
        this.known = known;
        this.expired = expired;
    }
}
