package com.lcx.security.oauth2.impl;

import com.google.inject.Inject;
import com.lcx.security.oauth2.*;
import com.lcx.security.oauth2.exceptions.OAuth2AccessTokenExpiredException;
import com.lcx.security.oauth2.exceptions.OAuth2AccessTokenInvalidException;
import com.lcx.security.oauth2.exceptions.OAuth2Exception;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * Created by Liam on 16/05/2015.
 */
public class OAuth2ClientImpl implements OAuth2Client {

    private final OAuth2Endpoint endpoint;
    private final OAuth2AccessTokenStore tokenStore;
    private final OAuthClient oAuthClient;
    private final OAuth2ResponseFactory responseFactory;

    @Inject
    public OAuth2ClientImpl(OAuth2Endpoint endpoint, OAuth2AccessTokenStore tokenStore,
                            OAuth2ResponseFactory responseFactory){

        this.tokenStore = tokenStore;
        this.endpoint = endpoint;
        this.responseFactory = responseFactory;

        oAuthClient = new OAuthClient(new URLConnectionClient());
    }

    public URI authorize() throws OAuth2Exception {

        try {
            OAuthClientRequest.AuthenticationRequestBuilder requestBuilder;

            requestBuilder = OAuthClientRequest
                        .authorizationLocation(endpoint.getAuthorizationUrl())
                        .setClientId(endpoint.getClientId())
                        .setRedirectURI(endpoint.getRedirectUrl())
                        .setResponseType("code");

            if(!isBlank(endpoint.getScopes())){
                requestBuilder.setScope(endpoint.getScopes());
            }

            OAuthClientRequest request = requestBuilder.buildQueryMessage();

            return new URI(request.getLocationUri());

        } catch (OAuthSystemException e) {
            throw new OAuth2Exception(e);
        } catch (URISyntaxException e) {
            throw new OAuth2Exception(e);
        }


    }

    public OAuth2AccessToken obtainToken(String oauthCode) throws OAuth2Exception {

        try {
            OAuthClientRequest request = OAuthClientRequest
                    .tokenLocation(endpoint.getTokenUrl())
                    .setGrantType(GrantType.AUTHORIZATION_CODE)
                    .setClientId(endpoint.getClientId())
                    .setClientSecret(endpoint.getClientSecret())
                    .setRedirectURI(endpoint.getRedirectUrl())
                    .setCode(oauthCode)
                    .buildBodyMessage();

            return retrieveAndStoreToken(request);

        } catch (OAuthSystemException e) {
            throw new OAuth2Exception(e);
        } catch (OAuthProblemException e) {
            throw new OAuth2Exception(e);
        }
    }


    public OAuth2AccessToken refreshToken(OAuth2AccessToken token) throws OAuth2Exception, OAuth2AccessTokenInvalidException {

        if(!tokenStore.exists(token))
            throw new OAuth2AccessTokenInvalidException();

        try {
            OAuth2AccessTokenMetaData metaData = tokenStore.getMetaData(token);

            OAuthClientRequest request = OAuthClientRequest
                    .tokenLocation(endpoint.getTokenUrl())
                    .setClientId(endpoint.getClientId())
                    .setClientSecret(endpoint.getClientSecret())
                    .setGrantType(GrantType.REFRESH_TOKEN)
                    .setRefreshToken(metaData.getRefreshToken())
                    .buildBodyMessage();

            OAuth2AccessToken newToken = retrieveAndStoreToken(request);

            //remove the old token
            tokenStore.removeToken(token);

            return newToken;

        } catch (OAuthSystemException e) {
            throw new OAuth2Exception(e);
        } catch (OAuthProblemException e) {
            throw new OAuth2Exception(e);
        }
    }

    public OAuth2AccessTokenStatus validateToken(OAuth2AccessToken token) {
        return new OAuth2AccessTokenStatus(tokenStore.exists(token), tokenStore.isExpired(token));
    }

    public OAuth2Response sendAuthorizedRequest(OAuth2AccessToken token, OAuth2Request request)
            throws OAuth2Exception, OAuth2AccessTokenExpiredException, OAuth2AccessTokenInvalidException {

        if(!tokenStore.exists(token))
            throw new OAuth2AccessTokenInvalidException();

        if(tokenStore.isExpired(token))
            throw new OAuth2AccessTokenExpiredException();

        try {
            OAuthClientRequest clientRequest;

            clientRequest = new OAuthBearerClientRequest(request.getUrl())
                    .setAccessToken(token.getAcessToken())
                    .buildQueryMessage();

            for(Map.Entry<String, String> entry: request.getHeaders().entrySet()){
                clientRequest.setHeader(entry.getKey(), entry.getValue());
            }

            if(request.hasContent())
                clientRequest.setBody(request.getContent());

            OAuthResourceResponse resourceResponse =
                    oAuthClient.resource(clientRequest, request.getHttpMethod().toString(), OAuthResourceResponse.class);

            return responseFactory.createResponse(
                    resourceResponse.getResponseCode(),
                    resourceResponse.getBody(),
                    resourceResponse.getContentType());

        } catch (OAuthSystemException e) {
            throw new OAuth2Exception(e);
        } catch (OAuthProblemException e) {
            throw new OAuth2Exception(e);
        }
    }


    private OAuth2AccessToken retrieveAndStoreToken(OAuthClientRequest request) throws OAuthProblemException, OAuthSystemException {

        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
        OAuthJSONAccessTokenResponse oAuthResponse = oAuthClient.accessToken(request);

        long expiresIn = oAuthResponse.getExpiresIn();
        String refreshToken = oAuthResponse.getRefreshToken();
        String accessToken = oAuthResponse.getAccessToken();

        OAuth2AccessToken oAuthAccessToken = new OAuth2AccessToken(accessToken);
        OAuth2AccessTokenMetaData oAuth2AccessTokenMetaData = new OAuth2AccessTokenMetaData(expiresIn, refreshToken);

        tokenStore.addToken(oAuthAccessToken, oAuth2AccessTokenMetaData);

        return oAuthAccessToken;
    }

    private boolean isBlank(String s){
        return s == null || s.equals("");
    }
}
